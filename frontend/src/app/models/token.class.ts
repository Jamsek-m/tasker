
export class Token {
    public id: number;
    public name: string;
    public expired: Date;
    public allowedActions: string[];
}

export interface ServiceToken {
    token: string;
}
