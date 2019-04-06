import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";

import {FormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from "./app-routing.module";
import {IconsModule} from "./icons.module";
import {BootstrapModule} from "./bootstrap.module";

import {AppComponent} from "./app.component";

import {LoginPageComponent} from "./pages/login-page/login-page.component";
import {ServiceListPageComponent} from "./pages/service-list-page/service-list-page.component";
import {CreateTokenPageComponent} from "./pages/create-token-page/create-token-page.component";
import {Error404PageComponent} from "./pages/error404-page/error404-page.component";
import {DocsPageComponent} from "./docs/docs-page.component";
import {DocsTaskPageComponent} from "./docs/docs-task-page/docs-task-page.component";
import {DocsPluginPageComponent} from "./docs/docs-plugin-page/docs-plugin-page.component";
import {ConfigurationPageComponent} from "./pages/configuration-page/configuration-page.component";
import {LogsPageComponent} from "./pages/logs-page/logs-page.component";
import {UserProfilePageComponent} from "./pages/user-profile-page/user-profile-page.component";

import {HeaderComponent} from "./components/header/header.component";
import {HeaderLinkComponent} from "./components/header/header-link/header-link.component";
import {PaginationComponent} from "./components/pagination/pagination.component";
import {PaginationLimitComponent} from "./components/pagination-limit/pagination-limit.component";

import {HttpApiInterceptor} from "./services/http.interceptor";
import { HealthOverviewComponent } from './components/health-overview/health-overview.component';
import { ServiceDetailsPageComponent } from './pages/service-details-page/service-details-page.component';
import { ServiceAddPageComponent } from './pages/service-add-page/service-add-page.component';
import { ServiceEditPageComponent } from './pages/service-edit-page/service-edit-page.component';


@NgModule({
    declarations: [
        AppComponent,
        ServiceListPageComponent,
        LoginPageComponent,
        Error404PageComponent,
        HeaderComponent,
        CreateTokenPageComponent,
        HeaderLinkComponent,
        PaginationComponent,
        PaginationLimitComponent,
        DocsPageComponent,
        DocsTaskPageComponent,
        DocsPluginPageComponent,
        ConfigurationPageComponent,
        LogsPageComponent,
        UserProfilePageComponent,
        HealthOverviewComponent,
        ServiceDetailsPageComponent,
        ServiceAddPageComponent,
        ServiceEditPageComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        AppRoutingModule,
        BootstrapModule,
        IconsModule
    ],
    providers: [
        {provide: HTTP_INTERCEPTORS, useClass: HttpApiInterceptor, multi: true}
    ],
    bootstrap: [AppComponent]
})
export class AppModule {

}
