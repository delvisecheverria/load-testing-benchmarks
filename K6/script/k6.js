import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
  // Ramp-up EXACTO como JMeter y Pulse
  stages: [
    { duration: '10s', target: 2 }, // ramp-up to 2 VUs over 10 seconds
  ],

  // Escenario que replica EXACTAMENTE el comportamiento de JMeter/Pulse
  scenarios: {
    jmeter_equivalent: {
      executor: 'per-vu-iterations', // cada VU hace N iteraciones
      vus: 2,                         // 🔥 2 usuarios virtuales
      iterations: 10,                 // 🔥 10 iteraciones por usuario
      // maxDuration eliminado ✔
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

  for (const p of paths) {
    http.get(`${host}${p}`, { headers });
  }

  sleep(1);
}
