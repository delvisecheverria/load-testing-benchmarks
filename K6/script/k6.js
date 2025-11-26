import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
  scenarios: {
    pulse_equivalent: {
      executor: 'ramping-vus',
      startVUs: 0,
      stages: [
        { duration: '120s', target: 25 },   // ramp-up a 25 VUs
        { duration: '180s', target: 25 },   // mantener hasta 300s total
      ],
      gracefulStop: '0s'
    }
  }
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

  // 🔥 Loop infinito hasta que se acabe el escenario (igual que Pulse)
  for (const p of paths) {
    http.get(`${host}${p}`, { headers });
  }

  sleep(1);  // pausa igual que Pulse
}
