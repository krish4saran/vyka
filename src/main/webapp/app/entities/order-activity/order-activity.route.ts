import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { OrderActivityComponent } from './order-activity.component';
import { OrderActivityDetailComponent } from './order-activity-detail.component';
import { OrderActivityPopupComponent } from './order-activity-dialog.component';
import { OrderActivityDeletePopupComponent } from './order-activity-delete-dialog.component';

@Injectable()
export class OrderActivityResolvePagingParams implements Resolve<any> {

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

export const orderActivityRoute: Routes = [
    {
        path: 'order-activity',
        component: OrderActivityComponent,
        resolve: {
            'pagingParams': OrderActivityResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderActivities'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'order-activity/:id',
        component: OrderActivityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderActivities'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orderActivityPopupRoute: Routes = [
    {
        path: 'order-activity-new',
        component: OrderActivityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderActivities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'order-activity/:id/edit',
        component: OrderActivityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderActivities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'order-activity/:id/delete',
        component: OrderActivityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'OrderActivities'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
