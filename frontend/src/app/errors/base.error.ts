export const ERR_TYPE_FIELD = "type";

export interface ErrorBody {
    message: string;
}

export class BaseError extends Error {
    public type: any;

    constructor(message: string) {
        super(message);
        Object.setPrototypeOf(this, BaseError.prototype);
    }
}
