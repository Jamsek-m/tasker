import {NgModule} from "@angular/core";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {faPlus, faTimes, faCopy, faCheck, faHeartbeat, faCogs, faWifi, faClipboard, faArrowRight, faQuestionCircle} from "@fortawesome/free-solid-svg-icons";

@NgModule({
    imports: [
        FontAwesomeModule
    ],
    exports: [
        FontAwesomeModule
    ]
})
export class IconsModule {

    // fas - free solid, far - free regular, fab - free brands

    constructor() {
        const icons = [
            faPlus,
            faTimes,
            faCopy,
            faCheck,
            faHeartbeat,
            faCogs,
            faWifi,
            faClipboard,
            faArrowRight,
            faQuestionCircle
        ];
        icons.forEach(icon => library.add(icon));
    }
}
