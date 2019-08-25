import {enableProdMode} from "@angular/core";
import {platformBrowserDynamic} from "@angular/platform-browser-dynamic";

import {AppModule} from "./app/app.module";
import {environment} from "./environments/environment";
import {AuthService} from "./app/services/auth.service";

if (environment.production) {
    enableProdMode();
}

AuthService.init(environment.keycloakJsonPath).then(() => {
    platformBrowserDynamic().bootstrapModule(AppModule)
        .catch(err => console.error(err));
}).catch(err => {
    console.error(err);
});

