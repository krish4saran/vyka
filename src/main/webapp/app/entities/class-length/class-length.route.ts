import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { ClassLengthComponent } from './class-length.component';
import { ClassLengthDetailComponent } from './class-length-detail.component';
import { ClassLengthPopupComponent } from './class-length-dialog.component';
import { ClassLengthDeletePopupComponent } from './class-length-delete-dialog.component';

export const classLengthRoute: Routes = [
    {
        path: 'class-length',
        component: ClassLengthComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClassLengths'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'class-length/:id',
        component: ClassLengthDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClassLengths'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const classLengthPopupRoute: Routes = [
    {
        path: 'class-length-new',
        component: ClassLengthPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClassLengths'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'class-length/:id/edit',
        component: ClassLengthPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClassLengths'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'class-length/:id/delete',
        component: ClassLengthDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClassLengths'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
