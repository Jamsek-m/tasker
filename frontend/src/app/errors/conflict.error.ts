import {BaseError} from "./base.error";

export class ConflictError extends BaseError {

    constructor(message: string) {
        super(message);
        this.type = ConflictError;
        Object.setPrototypeOf(this, ConflictError.prototype);
    }
}
