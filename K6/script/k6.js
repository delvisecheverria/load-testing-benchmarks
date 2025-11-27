import http from 'k6/http';
import { sleep } from 'k6';
import exec from 'k6/execution';

export const options = {
  scenarios: {
    pulse_equivalent: {
      executor: 'per-vu-iterations',
      vus: 1000,
      iterations: 1,       // ✔ solo 1 iteración por V
      maxDuration: '15m',
    }
  }
};

export default function () {

  const rampUp = 300;      // 120 segundos
  const totalVUs = 1000;
  const vuID = exec.vu.idInTest;

  // 🟦 Ramp-up manual perfecto:
  const delay = ((vuID - 1) * rampUp) / totalVUs;
  sleep(delay);

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

  // 🟩 1 sola iteración exacta
  for (const p of paths) {
    http.get(`${host}${p}`, { headers });
  }
}
