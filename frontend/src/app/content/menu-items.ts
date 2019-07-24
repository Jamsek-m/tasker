
export const menuItems: MenuItem[] = [
    {
        label: "Logs",
        url: "/logs",
        external: false,
        requireAuth: true
    },
    {
        label: "Config",
        url: "/config",
        external: false,
        requireAuth: true
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
}
