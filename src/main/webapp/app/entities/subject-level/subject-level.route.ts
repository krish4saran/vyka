import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SubjectLevelComponent } from './subject-level.component';
import { SubjectLevelDetailComponent } from './subject-level-detail.component';
import { SubjectLevelPopupComponent } from './subject-level-dialog.component';
import { SubjectLevelDeletePopupComponent } from './subject-level-delete-dialog.component';

export const subjectLevelRoute: Routes = [
    {
        path: 'subject-level',
        component: SubjectLevelComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubjectLevels'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'subject-level/:id',
        component: SubjectLevelDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubjectLevels'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const subjectLevelPopupRoute: Routes = [
    {
        path: 'subject-level-new',
        component: SubjectLevelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubjectLevels'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'subject-level/:id/edit',
        component: SubjectLevelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubjectLevels'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'subject-level/:id/delete',
        component: SubjectLevelDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SubjectLevels'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
