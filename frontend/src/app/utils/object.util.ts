export class ObjectUtil {

    public static isEmpty(obj: object): boolean {
        for (const prop in obj) {
            if (obj.hasOwnProperty(prop)) {
                return false;
            }
        }

        return JSON.stringify(obj) === JSON.stringify({});
    }
}
