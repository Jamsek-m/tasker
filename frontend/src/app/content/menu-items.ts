import {AuthRole} from "../models/enums/auth-role.enum";

export const menuItems: MenuItem[] = [
    {
        label: "Services",
        url: "/services",
        external: false,
        requireAuth: true,
    },
    {
        label: "Domains",
        url: "/domains",
        external: false,
        requireAuth: true,
        requiredRoles: [AuthRole.ADMIN]
    },
    {
        label: "Servers",
        url: "/servers",
        external: false,
        requireAuth: true,
        requiredRoles: [AuthRole.ADMIN]
    },
    {
        label: "Docker endpoints",
        url: "/docker-endpoints",
        external: false,
        requireAuth: true,
        requiredRoles: [AuthRole.ADMIN]
    },
    {
        label: "Logs",
        url: "/logs",
        external: false,
        requireAuth: true,
        requiredRoles: [AuthRole.ADMIN]
    },
    {
        label: "Docs",
        url: "/docs",
        external: false
    }
];


export interface MenuItem {
    label: string;
    url: string;
    external: boolean;
    requireAuth?: boolean;
    requiredRoles?: string[];
}
