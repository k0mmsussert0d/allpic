import { Role } from './role';

export interface User {
    id: number;
    username: string;
    email: string;
    registerTime: Date;
    isActive: boolean;
    role: Role;


}
