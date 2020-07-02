export interface Employee {
    [key: string]: string | number;
    id: number;
    firstName: string;
    lastName: string;
    designation: EmployeeDesignation;
    address: string;
    email: string;
}

export enum EmployeeDesignation {
    DEVELOPER = 'developer',
    MANAGER = 'manager',
}
