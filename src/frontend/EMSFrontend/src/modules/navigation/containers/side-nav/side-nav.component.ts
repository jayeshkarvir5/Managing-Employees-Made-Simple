import { ChangeDetectionStrategy, Component, Input, OnDestroy, OnInit } from '@angular/core';
import { AuthService, UserService } from '@modules/auth/services';
import { SideNavItems, SideNavSection } from '@modules/navigation/models';
import { NavigationService } from '@modules/navigation/services';
import { User } from '@testing/mocks';
import { Subscription } from 'rxjs';

@Component({
    selector: 'sb-side-nav',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './side-nav.component.html',
    styleUrls: ['side-nav.component.scss'],
})
export class SideNavComponent implements OnInit, OnDestroy {
    @Input() sidenavStyle!: string;
    @Input() sideNavItems!: SideNavItems;
    @Input() sideNavSections!: SideNavSection[];

    subscription: Subscription = new Subscription();
    routeDataSubscription!: Subscription;

    user!: User | null;
    constructor(public navigationService: NavigationService, public authService: AuthService) {
        var authUser:string|null = localStorage.getItem("AUTH_USER");
        if(authUser){
            this.user = JSON.parse(authUser);
        }
    }

    ngOnInit() {}

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
