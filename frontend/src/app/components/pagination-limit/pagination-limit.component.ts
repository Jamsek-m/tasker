import {Component, EventEmitter, OnInit, Output} from "@angular/core";

@Component({
    selector: "tasker-pagination-limit",
    templateUrl: "./pagination-limit.component.html",
    styleUrls: ["./pagination-limit.component.scss"]
})
export class PaginationLimitComponent implements OnInit {

    @Output()
    public whenLimitChanges = new EventEmitter<number>();

    constructor() {
    }

    ngOnInit() {
    }

    public changeLimit(newLimit: number) {
        this.whenLimitChanges.emit(newLimit);
    }

}
