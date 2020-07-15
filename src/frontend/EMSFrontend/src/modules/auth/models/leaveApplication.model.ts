import { User } from './auth.model';

export interface LeaveApplication {
        id: string;
        employee:User;
        startDate:string;
        endDate:string;
        approved: string;
    }
