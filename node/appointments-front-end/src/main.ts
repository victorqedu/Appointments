import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
/*import { enableProdMode } from '@angular/core';

if (process.env.NODE_ENV !== 'production') {
  // Disable HSTS in development environment
  const { applyHsts } = require('hsts');
  const hstsMiddleware = applyHsts({ maxAge: 0, includeSubDomains: false });
  hstsMiddleware(req, res, next);
}*/
platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
