import { User } from './auth.model';

export interface LeaveApplication {
        id: string;
        employee:User;
        days: string;
        approved: string;
    }
