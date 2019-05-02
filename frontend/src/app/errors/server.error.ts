import {BaseError} from "./base.error";

export class InternalServerError extends BaseError {
    constructor(message: string) {
        super(message);
        this.type = InternalServerError;
        Object.setPrototypeOf(this, InternalServerError.prototype);
    }
}
