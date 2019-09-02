export class ObjectUtil {

    public static isEmpty(obj: object): boolean {
        for (const prop in obj) {
            if (obj.hasOwnProperty(prop)) {
                return false;
            }
        }

        return JSON.stringify(obj) === JSON.stringify({});
    }

    public static isInteger(value: any): boolean {
        let x;
        if (isNaN(value)) {
            return false;
        }
        x = parseFloat(value);
        // tslint:disable-next-line:no-bitwise
        return (x | 0) === x;
    }
}
