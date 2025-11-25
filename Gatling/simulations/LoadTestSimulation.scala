name: Gatling Advanced Benchmark

on:
  workflow_dispatch:

jobs:
  gatling-advanced-test:
    runs-on: ubuntu-latest

    steps:
      # -------------------------------------------------------
      # 1. Checkout
      # -------------------------------------------------------
      - name: 📥 Checkout repo
        uses: actions/checkout@v4

      # -------------------------------------------------------
      # 2. Download Gatling Standalone
      # -------------------------------------------------------
      - name: 📦 Download Gatling
        run: |
          wget -q https://repo1.maven.org/maven2/io/gatling/highcharts/gatling-charts-highcharts-bundle/3.9.5/gatling-charts-highcharts-bundle-3.9.5-bundle.zip
          unzip gatling-charts-highcharts-bundle-3.9.5-bundle.zip
          mv gatling-charts-highcharts-bundle-3.9.5 gatling

      # -------------------------------------------------------
      # 3. Copy your simulation
      # -------------------------------------------------------
      - name: 📁 Copy LoadTestSimulation.scala
        run: |
          mkdir -p gatling/user-files/simulations
          cp Gatling/simulations/LoadTestSimulation.scala gatling/user-files/simulations/

      # -------------------------------------------------------
      # 4. Install monitors
      # -------------------------------------------------------
      - name: 📦 Install monitoring tools
        run: |
          sudo apt-get update
          sudo apt-get install -y sysstat gnuplot jq procps

      # -------------------------------------------------------
      # 5. Start monitors
      # -------------------------------------------------------
      - name: 🧪 Start monitors
        run: |
          pidstat -durh 1 > pidstat.log &
          echo $! > pidstat_pid.txt

          (while true; do
             top -b -n1 | head -30 >> top.log
             echo "---" >> top.log
             sleep 3
           done) &
          echo $! > top_pid.txt

          (while true; do
             PID=$(pgrep -f "gatling" || true)
             if [ ! -z "$PID" ]; then
               ps -T -p "$PID" >> threads_raw.log 2>/dev/null || true
             fi
             sleep 1
           done) &
          echo $! > threads_pid.txt

      # -------------------------------------------------------
      # 6. Run Gatling
      # -------------------------------------------------------
      - name: 🚀 Run Gatling benchmark
        run: |
          echo "Running Gatling benchmark..."
          mkdir -p results

          GATLING_OUT=$(mktemp)

          /usr/bin/time -v \
            ./gatling/bin/gatling.sh -s LoadTestSimulation \
            2> time_metrics.txt | tee "$GATLING_OUT"

          mv "$GATLING_OUT" results/gatling_stdout.txt
          echo "✔ Gatling test finished"

      # -------------------------------------------------------
      # 7. Stop monitors
      # -------------------------------------------------------
      - name: 🛑 Stop monitors
        if: always()
        run: |
          kill $(cat pidstat_pid.txt) || true
          kill $(cat top_pid.txt) || true
          kill $(cat threads_pid.txt) || true

      # -------------------------------------------------------
      # 8. Extract metrics
      # -------------------------------------------------------
      - name: 📉 Extract Gatling metrics (p95, p99, RPS)
        run: |
          REPORT=$(find gatling/results -name simulation.log | head -1)
          if [ -z "$REPORT" ]; then
            echo "❌ No Gatling logs found"
            exit 0
          fi

          awk -F',' '/REQUEST/ {print $9}' "$REPORT" | sort -n | awk '
            NR==int(NR*0.95){print $1 > "p95.txt"}
            NR==int(NR*0.99){print $1 > "p99.txt"}
          '

          awk -F',' '/REQUEST/ {sum++} END{print sum/10 > "rps.txt"}' "$REPORT"

      # -------------------------------------------------------
      # 9. Upload artifacts
      # -------------------------------------------------------
      - name: 📂 Upload Gatling Benchmark Artifacts
        if: always()
        uses: actions/upload-artifact@v4
        with:
          name: gatling-advanced-results
          path: |
            pidstat.log
            top.log
            threads_raw.log
            results/
            p95.txt
            p99.txt
            rps.txt
            time_metrics.txt
            Gatling/simulations/LoadTestSimulation.scala

