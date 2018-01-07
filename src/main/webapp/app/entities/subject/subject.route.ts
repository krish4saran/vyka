import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { SubjectComponent } from './subject.component';
import { SubjectDetailComponent } from './subject-detail.component';
import { SubjectPopupComponent } from './subject-dialog.component';
import { SubjectDeletePopupComponent } from './subject-delete-dialog.component';

export const subjectRoute: Routes = [
    {
        path: 'subject',
        component: SubjectComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Subjects'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'subject/:id',
        component: SubjectDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Subjects'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const subjectPopupRoute: Routes = [
    {
        path: 'subject-new',
        component: SubjectPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Subjects'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'subject/:id/edit',
        component: SubjectPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Subjects'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'subject/:id/delete',
        component: SubjectDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Subjects'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
