import {Component, Input, OnInit} from "@angular/core";

@Component({
    selector: "tasker-spiner",
    templateUrl: "./spiner.component.html",
    styleUrls: ["./spiner.component.scss"]
})
export class SpinerComponent implements OnInit {

    @Input()
    public size = 30;

    @Input()
    public width = 6;

    @Input()
    public mainColor = "#555";

    @Input()
    public secondaryColor = "#f3f3f3";

    @Input()
    public speed = 2;

    public border: string = null;
    public borderTop: string = null;
    public animation: string = null;

    constructor() {
    }

    ngOnInit() {
        this.border = `${this.width}px solid ${this.secondaryColor}`;
        this.borderTop = `${this.width}px solid ${this.mainColor}`;
        this.animation = `spin ${this.speed}s linear infinite`;
    }

}
