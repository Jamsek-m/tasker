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
import {HealthOverviewComponent} from "./components/health-overview/health-overview.component";
import {ServiceDetailsPageComponent} from "./pages/service-details-page/service-details-page.component";
import {ServiceFormPageComponent} from "./pages/service-form-page/service-form-page.component";
import {ConfirmationDialogComponent} from "./components/confirmation-dialog/confirmation-dialog.component";
import {ClipboardComponent} from "./components/clipboard/clipboard.component";
import {JsonViewComponent} from "./components/json-view/json-view.component";
import {NgJsonEditorModule} from "ang-jsoneditor";
import {HelpComponent} from "./components/help/help.component";
import {ServiceFormSummaryComponent} from "./pages/service-form-page/service-form-summary/service-form-summary.component";
import {SpinerComponent} from "./components/spiner/spiner.component";
import {DocsApiEntryComponent} from "./docs/docs-api-entry/docs-api-entry.component";
import {TokenGenerationModalComponent} from "./components/token-generation-modal/token-generation-modal.component";
import {NoopAnimationsModule} from "@angular/platform-browser/animations";
import {API_URL, BASE_URL} from "./injectables";
import {environment} from "../environments/environment";


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
        ServiceFormPageComponent,
        ConfirmationDialogComponent,
        ClipboardComponent,
        JsonViewComponent,
        HelpComponent,
        ServiceFormSummaryComponent,
        SpinerComponent,
        DocsApiEntryComponent,
        TokenGenerationModalComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        NoopAnimationsModule,
        AppRoutingModule,
        BootstrapModule,
        IconsModule,
        NgJsonEditorModule
    ],
    providers: [
        {provide: HTTP_INTERCEPTORS, useClass: HttpApiInterceptor, multi: true},
        {provide: API_URL, useValue: environment.baseUrl + "/" + environment.apiVersion},
        {provide: BASE_URL, useValue: environment.baseUrl}
    ],
    entryComponents: [
        ConfirmationDialogComponent,
        TokenGenerationModalComponent
    ],
    bootstrap: [AppComponent]
})
export class AppModule {

}
