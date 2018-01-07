import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { AvailabilityComponent } from './availability.component';
import { AvailabilityDetailComponent } from './availability-detail.component';
import { AvailabilityPopupComponent } from './availability-dialog.component';
import { AvailabilityDeletePopupComponent } from './availability-delete-dialog.component';

export const availabilityRoute: Routes = [
    {
        path: 'availability',
        component: AvailabilityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Availabilities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'availability/:id',
        component: AvailabilityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Availabilities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const availabilityPopupRoute: Routes = [
    {
        path: 'availability-new',
        component: AvailabilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Availabilities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'availability/:id/edit',
        component: AvailabilityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Availabilities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'availability/:id/delete',
        component: AvailabilityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Availabilities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
