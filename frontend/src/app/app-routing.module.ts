import {NgModule} from "@angular/core";
import {Routes, RouterModule} from "@angular/router";
import {ServiceListPageComponent} from "./pages/service-list-page/service-list-page.component";
import {LoginPageComponent} from "./pages/login-page/login-page.component";
import {Error404PageComponent} from "./pages/error404-page/error404-page.component";
import {DocsPageComponent} from "./docs/docs-page.component";
import {DockerEndpointsPageComponent} from "./pages/docker-endpoints-page/docker-endpoints-page.component";
import {LogsPageComponent} from "./pages/logs-page/logs-page.component";
import {AuthGuard} from "./services/auth.guard";
import {ServiceDetailsPageComponent} from "./pages/service-details-page/service-details-page.component";
import {ServiceFormPageComponent} from "./pages/service-form-page/service-form-page.component";
import {DashboardPageComponent} from "./pages/dashboard-page/dashboard-page.component";
import {DomainListPageComponent} from "./pages/domain-list-page/domain-list-page.component";
import {Error403PageComponent} from "./pages/error403-page/error403-page.component";
import {GuardRoles} from "./models/auth.models";
import {RoleGuard} from "./services/role.guard";
import {AuthRole} from "./models/enums/auth-role.enum";
import {AuthRedirectComponent} from "./pages/auth-redirect/auth-redirect.component";

const routes: Routes = [
    {path: "", pathMatch: "full", component: DashboardPageComponent},
    {path: "domains", component: DomainListPageComponent, canActivate: [RoleGuard], data: GuardRoles.withRoles([AuthRole.ADMIN])},
    {path: "services", component: ServiceListPageComponent, canActivate: [AuthGuard]},
    {path: "service/add", component: ServiceFormPageComponent, canActivate: [AuthGuard]},
    {path: "service/new-version", component: ServiceFormPageComponent, canActivate: [AuthGuard]},
    {path: "service/:id/edit", component: ServiceFormPageComponent, canActivate: [AuthGuard]},
    {path: "service/:id", component: ServiceDetailsPageComponent, canActivate: [AuthGuard]},
    {path: "logs", component: LogsPageComponent, canActivate: [RoleGuard], data: GuardRoles.withRoles([AuthRole.ADMIN])},
    {path: "docker-endpoints", component: DockerEndpointsPageComponent, canActivate: [RoleGuard], data: GuardRoles.withRoles([AuthRole.ADMIN])},
    {path: "docs", component: DocsPageComponent},
    {path: "login", component: LoginPageComponent},
    {path: "auth", component: AuthRedirectComponent},
    {path: "404", component: Error404PageComponent},
    {path: "403", component: Error403PageComponent},
    {path: "**", redirectTo: "/404"}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
