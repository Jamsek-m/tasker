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
import {DockerEndpointsPageComponent} from "./pages/docker-endpoints-page/docker-endpoints-page.component";
import {LogsPageComponent} from "./pages/logs-page/logs-page.component";

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
import {API_URL, BASE_URL, TASKER_META} from "./injectables";
import {environment} from "../environments/environment";
import {DashboardPageComponent} from "./pages/dashboard-page/dashboard-page.component";
import {AuthInterceptor} from "./services/auth.interceptor";
import {DomainListPageComponent} from "./pages/domain-list-page/domain-list-page.component";
import {ServiceFormClientSectionComponent} from "./pages/service-form-page/service-form-client-section/service-form-client-section.component";
import {ServiceFormApiSectionComponent} from "./pages/service-form-page/service-form-api-section/service-form-api-section.component";
import {Error403PageComponent} from "./pages/error403-page/error403-page.component";
import {CancelIconComponent} from "./components/cancel-icon/cancel-icon.component";
import {AuthRedirectComponent} from "./pages/auth-redirect/auth-redirect.component";


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
        DockerEndpointsPageComponent,
        LogsPageComponent,
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
        TokenGenerationModalComponent,
        DashboardPageComponent,
        DomainListPageComponent,
        ServiceFormClientSectionComponent,
        ServiceFormApiSectionComponent,
        Error403PageComponent,
        CancelIconComponent,
        AuthRedirectComponent
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
        {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},
        {provide: API_URL, useValue: environment.baseUrl + "/" + environment.apiVersion},
        {provide: BASE_URL, useValue: environment.baseUrl},
        {provide: TASKER_META, useValue: environment.projectMeta}
    ],
    entryComponents: [
        ConfirmationDialogComponent,
        TokenGenerationModalComponent
    ],
    bootstrap: [AppComponent]
})
export class AppModule {

}
