import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Input,
    OnDestroy,
    OnInit,
} from '@angular/core';
import { sideNavItems, sideNavSections } from '@modules/navigation/data';
import { NavigationService } from '@modules/navigation/services';
import { Subscription } from 'rxjs';
import { AuthService } from '@modules/auth/services';

@Component({
    selector: 'sb-layout-dashboard',
    changeDetection: ChangeDetectionStrategy.OnPush,
    templateUrl: './layout-dashboard.component.html',
    styleUrls: ['layout-dashboard.component.scss'],
})
export class LayoutDashboardComponent implements OnInit, OnDestroy {
    @Input() static = false;
    @Input() light = false;
    @HostBinding('class.sb-sidenav-toggled') sideNavHidden = false;
    subscription: Subscription = new Subscription();
    sideNavItems = sideNavItems;
    sideNavSections = sideNavSections;
    sidenavStyle = 'sb-sidenav-dark';

    constructor(
        public navigationService: NavigationService,
        private changeDetectorRef: ChangeDetectorRef,
        private authService: AuthService
    ) {}
    ngOnInit() {
        if (this.light) {
            this.sidenavStyle = 'sb-sidenav-light';
        }
        this.subscription.add(
            this.navigationService.sideNavVisible$().subscribe(isVisible => {
                this.sideNavHidden = !isVisible;
                this.changeDetectorRef.markForCheck();
            })
        );

        if (this.authService.getAuthUser()?.designation !== 'Admin') {
            // remove admin section
            this.sideNavSections = this.sideNavSections.filter(e => e.text && e.text !== 'Admin');
        }
    }
    ngOnDestroy() {
        this.subscription.unsubscribe();
    }
}
