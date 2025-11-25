import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
  // 🔥 Ramp-up EXACTO equivalente a JMeter y Pulse
  stages: [
    { duration: '10s', target: 2 }, // subir hasta 2 VUs en 10 segundos
  ],

  // 🔥 ESCENARIO equivalente 1:1 a JMeter y Pulse
  scenarios: {
    jmeter_equivalent: {
      executor: 'per-vu-iterations',
      vus: 2,           // 2 usuarios
      iterations: 10,   // 10 iteraciones por usuario (total 20)
      gracefulStop: '0s',
    },
  },
};

export default function () {
  const headers = {
    'User-Agent': 'Mozilla/5.0',
    'Accept': 'text/html',
    'Accept-Language': 'en-US,en;q=0.5',
    'Connection': 'keep-alive',
  };

  const host = 'http://www.testingyes.com';

  const paths = [
    '/onlineshop/',
    '/onlineshop/prices-drop',
    '/onlineshop/new-products',
    '/onlineshop/best-sales',
    '/onlineshop/content/1-delivery',
    '/onlineshop/content/2-legal-notice',
    '/onlineshop/content/3-terms-and-conditions-of-use',
    '/onlineshop/content/4-about-us',
    '/onlineshop/content/5-secure-payment',
    '/onlineshop/contact-us',
    '/onlineshop/sitemap',
    '/onlineshop/stores',
    '/onlineshop/identity',
    '/onlineshop/login?back=identity',
    '/onlineshop/order-history',
    '/onlineshop/login?back=history',
    '/onlineshop/credit-slip',
    '/onlineshop/login?back=order-slip',
    '/onlineshop/addresses',
    '/onlineshop/login?back=addresses',
  ];

  // 🔥 Cada iteración ejecuta todas las rutas
  for (const p of paths) {
    http.get(`${host}${p}`, { headers });
  }

  sleep(1); // igual que think time en JMeter y Pulse
}
