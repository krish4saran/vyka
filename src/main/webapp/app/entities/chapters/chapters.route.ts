import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { ChaptersComponent } from './chapters.component';
import { ChaptersDetailComponent } from './chapters-detail.component';
import { ChaptersPopupComponent } from './chapters-dialog.component';
import { ChaptersDeletePopupComponent } from './chapters-delete-dialog.component';

export const chaptersRoute: Routes = [
    {
        path: 'chapters',
        component: ChaptersComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Chapters'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'chapters/:id',
        component: ChaptersDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Chapters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chaptersPopupRoute: Routes = [
    {
        path: 'chapters-new',
        component: ChaptersPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Chapters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'chapters/:id/edit',
        component: ChaptersPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Chapters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'chapters/:id/delete',
        component: ChaptersDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Chapters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
