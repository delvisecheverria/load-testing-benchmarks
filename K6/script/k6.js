import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
  stages: [
    { duration: '300s', target: 100 },   // ramp-up hasta 1000 users
    { duration: '600s', target: 100 },   // tiempo para que TODOS terminen su flujo
  ],
  gracefulStop: '0s',   // parar tan pronto terminen los usuarios activos
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

  // Un solo ciclo — equivalente a "iterations: 1" de Pulse
  for (const p of paths) {
    http.get(`${host}${p}`, { headers });
  }

  // Pequeña espera para que k6 cierre bien la iteración
  sleep(1);
}
