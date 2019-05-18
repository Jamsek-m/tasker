import {NgModule} from "@angular/core";
import {Routes, RouterModule} from "@angular/router";
import {ServiceListPageComponent} from "./pages/service-list-page/service-list-page.component";
import {LoginPageComponent} from "./pages/login-page/login-page.component";
import {Error404PageComponent} from "./pages/error404-page/error404-page.component";
import {DocsPageComponent} from "./docs/docs-page.component";
import {DocsTaskPageComponent} from "./docs/docs-task-page/docs-task-page.component";
import {DocsPluginPageComponent} from "./docs/docs-plugin-page/docs-plugin-page.component";
import {ConfigurationPageComponent} from "./pages/configuration-page/configuration-page.component";
import {LogsPageComponent} from "./pages/logs-page/logs-page.component";
import {AuthGuard} from "./services/auth.guard";
import {UserProfilePageComponent} from "./pages/user-profile-page/user-profile-page.component";
import {ServiceDetailsPageComponent} from "./pages/service-details-page/service-details-page.component";
import {ServiceFormPageComponent} from "./pages/service-form-page/service-form-page.component";

const routes: Routes = [
    {path: "", pathMatch: "full", component: ServiceListPageComponent, canActivate: [AuthGuard]},
    {path: "service/add", component: ServiceFormPageComponent, canActivate: [AuthGuard]},
    {path: "service/new-version", component: ServiceFormPageComponent, canActivate: [AuthGuard]},
    {path: "service/:id/edit", component: ServiceFormPageComponent, canActivate: [AuthGuard]},
    {path: "service/:id", component: ServiceDetailsPageComponent, canActivate: [AuthGuard]},
    {path: "logs", component: LogsPageComponent, canActivate: [AuthGuard]},
    {path: "config", component: ConfigurationPageComponent, canActivate: [AuthGuard]},
    {path: "docs", component: DocsPageComponent, canActivate: [AuthGuard]},
    {path: "docs/task/:name", component: DocsTaskPageComponent, canActivate: [AuthGuard]},
    {path: "docs/plugin/:name", component: DocsPluginPageComponent, canActivate: [AuthGuard]},
    {path: "profile", component: UserProfilePageComponent, canActivate: [AuthGuard]},
    {path: "login", component: LoginPageComponent},
    {path: "404", component: Error404PageComponent},
    {path: "**", redirectTo: "/404"}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
