import {EventEmitter, Inject, Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
import {User} from "../models/user.class";
import {API_URL} from "../injectables";
import {KeycloakInitOptions, KeycloakInstance} from "keycloak-js";
import * as Keycloak_ from "keycloak-js";

const Keycloak = Keycloak_;

@Injectable({
    providedIn: "root"
})
export class AuthService {

    private static auth: KeycloakInstance = null;

    private eventEmitter: EventEmitter<string | null> = new EventEmitter<string | null>();

    constructor(
        @Inject(API_URL) private apiUrl: string,
        private http: HttpClient) {
    }

    public static init(keycloakJsonPath: string): Promise<void> {
        const keycloakAuth: KeycloakInstance = Keycloak(keycloakJsonPath);

        const config: KeycloakInitOptions = {
            onLoad: "check-sso"
        };

        return new Promise<void>((resolve, reject) => {
            keycloakAuth.init(config).success(() => {
                AuthService.auth = keycloakAuth;
                resolve();
            }).error(() => {
                reject();
            });
        });
    }

    public refreshToken(): Promise<void> {

        return new Promise<void>((resolve, reject) => {
            const payload = this.getTokenPayload();
            if (!payload) {
                reject("Can't get access token payload!");
            }

            const issuedAt = new Date(parseInt(payload["iat"], 10) * 1000);
            const expiresAt = new Date(parseInt(payload["exp"], 10) * 1000);
            const now = new Date();
            const timeUntilExpiry = expiresAt.getTime() - now.getTime();
            const validityTime = (expiresAt.getTime() - issuedAt.getTime()) * 2;

            if (timeUntilExpiry <= 30000) {
                // token is about to expire
                if (timeUntilExpiry > 0) {
                    // token is not already expired
                    AuthService.auth.updateToken(validityTime).success(() => {
                        resolve();
                    }).error(err => {
                        reject(err);
                    });
                } else {
                    // access token has already expired
                    const refreshTokenPayload = this.getRefreshTokenPayload();
                    if (!refreshTokenPayload) {
                        throw new Error("Can't get refresh token payload!");
                    }
                    const refreshExpiresAt = new Date(parseInt(refreshTokenPayload["exp"], 10) * 1000);
                    const timeUntilRefreshExpiry = refreshExpiresAt.getTime() - now.getTime();

                    if (timeUntilRefreshExpiry > 30000) {
                        // refresh token is still valid - update access token
                        AuthService.auth.updateToken(validityTime).success(() => {
                            resolve();
                        }).error(err => {
                            reject(err);
                        });
                    } else {
                        // refresh token is about to expire, logout
                        reject("Token is about to be expired! Logging out...");
                    }
                }
            } else {
                // token is still valid
                resolve();
            }
        });
    }

    /**
     * Returns payload of an access token
     */
    public getTokenPayload(): any {
        if (AuthService.auth.tokenParsed) {
            return AuthService.auth.tokenParsed;
        }
        return null;
    }

    /**
     * Returns payload of a refresh token
     */
    public getRefreshTokenPayload(): any {
        if (AuthService.auth.refreshTokenParsed) {
            return AuthService.auth.refreshTokenParsed;
        }
        return null;
    }

    public logout(): void {
        AuthService.auth.logout();
    }

    public isAuthenticated(): boolean {
        return AuthService.auth.authenticated;
    }

    /**
     * Get raw token
     */
    public getToken(): string {
        if (AuthService.auth.token) {
            return AuthService.auth.token;
        } else {
            return null;
        }
    }

    public redirectToLogin(): void {
        AuthService.auth.login();
    }

    /** Returns user id
     * @returns user id
     */
    public getUserId(): string {
        return AuthService.auth.subject;
    }

    /**
     * Checks whether user has required role in realm roles
     * @param role required role
     */
    public hasRealmRole(role: string): boolean {
        return this.isAuthenticated() && AuthService.auth.hasRealmRole(role);
    }

    /**
     * Checks whether user has required role in given client roles
     * @param role required role
     * @param resource client name
     */
    public hasResourceRole(role: string, resource: string): boolean {
        return this.isAuthenticated() && AuthService.auth.hasResourceRole(role, resource);
    }

    /**
     * Checks whether user has required role. Role is checked in realm roles and in default client roles
     * @param role required role
     */
    public hasRole(role: string): boolean {
        return this.hasRealmRole(role) || this.hasResourceRole(role, "tasker-public");
    }

    public getUserUsername(): string {
        return (AuthService.auth.tokenParsed as any)["preferred_username"];
    }

    public getUserEmail(): string {
        return (AuthService.auth.tokenParsed as any)["email"];
    }
    public userHasVerifiedEmail(): string {
        return (AuthService.auth.tokenParsed as any)["email_verified"];
    }
}
