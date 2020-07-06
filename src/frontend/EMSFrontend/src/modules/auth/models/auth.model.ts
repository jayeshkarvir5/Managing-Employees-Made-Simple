export interface User {
    id: string;
    firstName: string;
    lastName: string;
    email: string;
    phoneNo?: string;
    designation?: string;
    address?: string;
    experience?: number;
    leaveApp?: boolean;
    projects?: Array<any>;
    techstack?: string;
}
