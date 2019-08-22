import {NgModule} from "@angular/core";
import {FontAwesomeModule, FaIconLibrary} from "@fortawesome/angular-fontawesome";
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
    faBolt, faSearch, faArrowLeft, faExclamationTriangle, faPencilAlt, faLock
} from "@fortawesome/free-solid-svg-icons";
import {faDocker} from "@fortawesome/free-brands-svg-icons";
import {
    faCopy as farCopy,
    faTrashAlt as farTrashAlt,
    faWindowRestore as farWindowRestore
} from "@fortawesome/free-regular-svg-icons";

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
    constructor(library: FaIconLibrary) {
        library.addIcons(
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
            farCopy,
            faLock,
            farTrashAlt,
            farWindowRestore
        );
    }
}
