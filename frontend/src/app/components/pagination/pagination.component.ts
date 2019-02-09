import {Component, EventEmitter, Input, OnInit, Output} from "@angular/core";

@Component({
    selector: "tasker-pagination",
    templateUrl: "./pagination.component.html",
    styleUrls: ["./pagination.component.scss"]
})
export class PaginationComponent implements OnInit {

    @Input()
    public totalItems = 0;

    @Input()
    public itemsPerPage = 10;

    @Output()
    public whenPageChange = new EventEmitter<number>();

    @Output()
    public whenItemsPerPageChange = new EventEmitter<number>();

    public currentPage = 0;

    private FRONT_INDEX_LIMIT = 2;
    private END_INDEX_LIMIT = 2;

    constructor() {
    }

    ngOnInit() {

    }

    get totalPages() {
        return Math.ceil(this.totalItems / this.itemsPerPage);
    }

    get shownPages(): number[] {
        const pagesBeforeCurrent = this.determineTilesInFront(this.currentPage, this.totalPages);
        const pagesAfterCurrent = this.determineTilesOnEnd(this.currentPage, this.totalPages);
        return this.getShownPages(pagesBeforeCurrent, this.currentPage, pagesAfterCurrent);
    }

    public activatePage(pageIndex: number) {
        this.currentPage = pageIndex;
        this.whenPageChange.emit(pageIndex);
    }

    public activatePositionedPage(pageIndex: number) {
        if (pageIndex >= 0 && pageIndex < this.totalPages) {
            this.activatePage(pageIndex);
        }
    }

    private getShownPages(itemsBefore: number, currentPage: number, itemsAfter: number): number[] {
        const arr = [];
        for (let i = currentPage - itemsBefore; i < currentPage; i++) {
            arr.push(i);
        }
        arr.push(currentPage);
        for (let i = currentPage + 1; i <= currentPage + itemsAfter; i++) {
            arr.push(i);
        }
        return arr;
    }

    private determineTilesInFront(pageIndex: number, totalPages: number): number {
        if (pageIndex < 0) {
            throw new Error("[PaginationComponent] Invalid state! Negative page index.");
        } else if (pageIndex === 0) {
            return 0;
        } else if (pageIndex === 1) {
            return 1;
        }
        return this.FRONT_INDEX_LIMIT;
    }

    private determineTilesOnEnd(pageIndex: number, totalPages: number): number {
        const index = Math.abs(pageIndex - totalPages);
        if (index === 1) {
            return 0;
        } else if (index === 2) {
            return 1;
        }
        return this.END_INDEX_LIMIT;
    }

}
