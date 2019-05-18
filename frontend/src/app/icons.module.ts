import {NgModule} from "@angular/core";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {library} from "@fortawesome/fontawesome-svg-core";
import {
    faPlus,
    faTimes,
    faCopy,
    faCheck,
    faHeartbeat,
    faCogs,
    faClipboard,
    faArrowRight,
    faQuestionCircle,
    faBolt, faSearch, faArrowLeft, faExclamationTriangle, faPencilAlt
} from "@fortawesome/free-solid-svg-icons";
import {faDocker} from "@fortawesome/free-brands-svg-icons";
import {faCopy as farCopy} from "@fortawesome/free-regular-svg-icons";

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
            faClipboard,
            faArrowRight,
            faQuestionCircle,
            faDocker,
            faBolt,
            faSearch,
            faArrowLeft,
            faExclamationTriangle,
            faPencilAlt,
            farCopy
        ];
        icons.forEach(icon => library.add(icon));
    }
}
