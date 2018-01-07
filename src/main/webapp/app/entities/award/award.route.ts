import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { AwardComponent } from './award.component';
import { AwardDetailComponent } from './award-detail.component';
import { AwardPopupComponent } from './award-dialog.component';
import { AwardDeletePopupComponent } from './award-delete-dialog.component';

export const awardRoute: Routes = [
    {
        path: 'award',
        component: AwardComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Awards'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'award/:id',
        component: AwardDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Awards'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const awardPopupRoute: Routes = [
    {
        path: 'award-new',
        component: AwardPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Awards'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'award/:id/edit',
        component: AwardPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Awards'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'award/:id/delete',
        component: AwardDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Awards'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
