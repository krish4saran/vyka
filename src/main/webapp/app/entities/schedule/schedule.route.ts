import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ScheduleComponent } from './schedule.component';
import { ScheduleDetailComponent } from './schedule-detail.component';
import { SchedulePopupComponent } from './schedule-dialog.component';
import { ScheduleDeletePopupComponent } from './schedule-delete-dialog.component';

@Injectable()
export class ScheduleResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const scheduleRoute: Routes = [
    {
        path: 'schedule',
        component: ScheduleComponent,
        resolve: {
            'pagingParams': ScheduleResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Schedules'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'schedule/:id',
        component: ScheduleDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Schedules'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const schedulePopupRoute: Routes = [
    {
        path: 'schedule-new',
        component: SchedulePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Schedules'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'schedule/:id/edit',
        component: SchedulePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Schedules'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'schedule/:id/delete',
        component: ScheduleDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Schedules'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
