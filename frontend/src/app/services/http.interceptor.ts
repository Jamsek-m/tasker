import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable({
    providedIn: "root"
})
export class HttpApiInterceptor implements HttpInterceptor {

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const headers = req.headers
            .set("Content-Type", "application/json")
            .set("Access-Control-Allow-Credentials", "true");

        return next.handle(req.clone({
            headers,
            withCredentials: true
        }));
    }

}
