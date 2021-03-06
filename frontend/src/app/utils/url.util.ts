export class UrlUtil {

    public static buildUrl(url: string): string | null {
        if (!url) {
            return null;
        }
        let newUrl = decodeURIComponent(url);
        newUrl = newUrl
            .trim()
            .replace(/\s/g, "");
        if (/^(:\/\/)/.test(newUrl)) {
            return `http${newUrl}`;
        }
        if (!/^(f|ht)tps?:\/\//i.test(newUrl)) {
            return `http://${newUrl}`;
        }
        return newUrl;
    }
}
