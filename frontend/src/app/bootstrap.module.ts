import {NgModule} from "@angular/core";
import {AlertModule, BsDatepickerModule, ModalModule, TimepickerModule, TooltipModule} from "ngx-bootstrap";

@NgModule({
    imports: [
        TooltipModule.forRoot(),
        ModalModule.forRoot(),
        AlertModule.forRoot(),
        BsDatepickerModule.forRoot(),
        TimepickerModule.forRoot()
    ],
    exports: [
        TooltipModule,
        ModalModule,
        AlertModule,
        BsDatepickerModule,
        TimepickerModule
    ]
})
export class BootstrapModule {

}
