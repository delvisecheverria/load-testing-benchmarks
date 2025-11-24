import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
    vus: 2,
    iterations: 20,
    stages: [
        { duration: '10s', target: 2 },
        { duration: '0s', target: 2 },
    ],
};

const headers = {
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
    'Accept-Encoding': 'gzip, deflate',
    'Accept-Language': 'en-US,en;q=0.5',
    'Connection': 'keep-alive',
    'Upgrade-Insecure-Requests': '1',
    'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:145.0) Gecko/20100101 Firefox/145.0',
};

const BASE = 'http://www.testingyes.com';

export default function() {
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

    for (const path of paths) {
        http.get(`${BASE}${path}`, { headers });
        sleep(0.1);
    }
}

