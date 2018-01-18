import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ScheduleActivityComponent } from './schedule-activity.component';
import { ScheduleActivityDetailComponent } from './schedule-activity-detail.component';
import { ScheduleActivityPopupComponent } from './schedule-activity-dialog.component';
import { ScheduleActivityDeletePopupComponent } from './schedule-activity-delete-dialog.component';

export const scheduleActivityRoute: Routes = [
    {
        path: 'schedule-activity',
        component: ScheduleActivityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ScheduleActivities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'schedule-activity/:id',
        component: ScheduleActivityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ScheduleActivities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const scheduleActivityPopupRoute: Routes = [
    {
        path: 'schedule-activity-new',
        component: ScheduleActivityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ScheduleActivities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'schedule-activity/:id/edit',
        component: ScheduleActivityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ScheduleActivities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'schedule-activity/:id/delete',
        component: ScheduleActivityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ScheduleActivities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
