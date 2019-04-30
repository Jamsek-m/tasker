import {NgModule} from "@angular/core";
import {AlertModule, BsDatepickerModule, ModalModule, TimepickerModule, TooltipModule, TypeaheadModule} from "ngx-bootstrap";
import {ToastaModule} from "ngx-toasta";

@NgModule({
    imports: [
        TooltipModule.forRoot(),
        ModalModule.forRoot(),
        AlertModule.forRoot(),
        BsDatepickerModule.forRoot(),
        TimepickerModule.forRoot(),
        ToastaModule.forRoot(),
        TypeaheadModule.forRoot()
    ],
    exports: [
        TooltipModule,
        ModalModule,
        AlertModule,
        BsDatepickerModule,
        TimepickerModule,
        ToastaModule,
        TypeaheadModule
    ]
})
export class BootstrapModule {

}
