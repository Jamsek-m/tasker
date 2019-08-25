
export class GuardRoles {

    public requiredRoles: string[];

    public static withRoles(requiredRoles: string[]): GuardRoles {
        const data = new GuardRoles();
        data.requiredRoles = requiredRoles;
        return data;
    }
}
