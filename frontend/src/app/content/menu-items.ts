
export const menuItems: MenuItem[] = [
    {
        label: "Logs",
        url: "/logs",
        external: false
    },
    {
        label: "Config",
        url: "/config",
        external: false
    },
    {
        label: "Docs",
        url: "/docs",
        external: false
    },
    {
        label: "Jenkins",
        url: "https://jenkins.mjamsek.com",
        external: true
    }
];


export interface MenuItem {
    label: string;
    url: string;
    external: boolean;
}
