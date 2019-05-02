
export class StringUtil {

    /**
     * Recursively trims all non-null properties of given object.
     * @param obj Object whose properties to trim
     */
    public static trimAllProperties(obj: object): void {
        Object.keys(obj).forEach((key: string) => {
            if ((obj as any)[key] && typeof (obj as any)[key] === "string") {
                (obj as any)[key] = (obj as any)[key].trim();
            } else if ((obj as any)[key] && typeof (obj as any)[key] === "object") {
                StringUtil.trimAllProperties((obj as any)[key]);
            }
        });
    }

}
