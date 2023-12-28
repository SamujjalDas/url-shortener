# url-shortener
This repository contains backend service for url-shortener app
Language - Java
Framework - Springboot
Open for collaborations. PRs for any new features or bug fix will be reviewed and merged.
Thank You.

How to run the application for local development
1. Setup and run postgres db in local and update the credentials in application.properties
2. Run UrlShortenerApplication
3. Once application is up, open http://localhost:8080/test 
4. 'Service is Up' - This will be displayed in browser

Curl documentation
1. Generate short url
Request : 
   curl --location 'http://localhost:8080/generate' \
   --header 'Content-Type: application/json' \
   --data '{
   "url": "https://www.amazon.in/Lenovo-IdeaPad-Gaming-39-62cm-82K201V2IN/dp/B0B7RXC1Y1?ref_=Oct_DLandingS_D_416a10e7_70&th=1"
   }'
Response : 
   {
   "originalUrl": "https://www.amazon.in/Lenovo-IdeaPad-Gaming-39-62cm-82K201V2IN/dp/B0B7RXC1Y1?ref_=Oct_DLandingS_D_416a10e7_70&th=1",
   "shortLink": "fdefa25f",
   "expirationDate": "2023-02-19T13:19:26.3750891"
   }
2. In browser open baseurl+shortLink. Eg: http://localhost:8080/fdefa25f 
   User will be redirected to original url

   Use Snaply UI to shorten the URLs from web
   URL : http://localhost:8080/

**Snaply is live now @ https://www.snaply.lat
**

<img src="https://github.com/SamujjalDas/url-shortener/assets/34987253/33875360-4d59-4980-a96d-5208774756f3" width="400">
<img src="https://github.com/SamujjalDas/url-shortener/assets/34987253/1e0707a9-6b43-4689-b9a2-9d6bca5f5e75" width="400">



