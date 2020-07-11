import { SideNavItems, SideNavSection } from '@modules/navigation/models';

export const sideNavSections: SideNavSection[] = [
    {
        text: 'Personal',
        items: ['dashboard'],
    },
    {
        text: 'Company',
        items: ['employees'],
    },
    {
        text:'Leave Management',
        items:['leaves'],
    },
    {
        text: 'Settings',
        items: ['accounts'],
    },
    {
        text: 'Admin',
        items: ['newUser', 'newProject'],
    },

    // {
    //     text: 'INTERFACE',
    //     items: ['layouts', 'pages'],
    // },
    // {
    //     text: 'ADDONS',
    //     items: ['charts', 'tables'],
    // },
];

export const sideNavItems: SideNavItems = {
    dashboard: {
        icon: 'tachometer-alt',
        text: 'Dashboard',
        link: '/dashboard',
    },
    employees: {
        icon: '',
        text: 'Employees',
        link: '/employees',
    },
    leaves:{
        icon:'',
        text:'Leave Applications',
        submenu: [
            {
                text: 'Track My Leaves',
                link: '/leaveapplication/track',
            },
            {
                text: 'Manage Employee Leaves',
                link: '/leaveapplication/manage',
            },
        ],
    },
    accounts: {
        icon: '',
        text: 'Account',
        submenu: [
            {
                text: 'View your Profile',
                link: '/',
            },
            {
                text: 'Update Profile',
                link: '/',
            },
            {
                text: 'Logout',
                link: '/logout',
            },
        ],
    },
    newUser: {
        icon: '',
        text: 'Create a new User',
        link: '/admin/new-user',
    },
    newProject: {
        icon: '',
        text: 'Create a new Project',
        link: '/admin/new-project',
    },
    // layouts: {
    //     icon: 'columns',
    //     text: 'Layouts',
    //     submenu: [
    //         {
    //             text: 'Static Navigation',
    //             link: '/dashboard/static',
    //         },
    //         {
    //             text: 'Light Sidenav',
    //             link: '/dashboard/light',
    //         },
    //     ],
    // },
    // pages: {
    //     icon: 'book-open',
    //     text: 'Pages',
    //     submenu: [
    //         {
    //             text: 'Authentication',
    //             submenu: [
    //                 {
    //                     text: 'Login',
    //                     link: '/auth/login',
    //                 },
    //                 {
    //                     text: 'Register',
    //                     link: '/auth/register',
    //                 },
    //                 {
    //                     text: 'Forgot Password',
    //                     link: '/auth/forgot-password',
    //                 },
    //             ],
    //         },
    //         {
    //             text: 'Error',
    //             submenu: [
    //                 {
    //                     text: '401 Page',
    //                     link: '/error/401',
    //                 },
    //                 {
    //                     text: '404 Page',
    //                     link: '/error/404',
    //                 },
    //                 {
    //                     text: '500 Page',
    //                     link: '/error/500',
    //                 },
    //             ],
    //         },
    //     ],
    // },
    // charts: {
    //     icon: 'chart-area',
    //     text: 'Charts',
    //     link: '/charts',
    // },
    // tables: {
    //     icon: 'table',
    //     text: 'Tables',
    //     link: '/tables',
    // },
};
