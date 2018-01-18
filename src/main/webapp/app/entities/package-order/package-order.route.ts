import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PackageOrderComponent } from './package-order.component';
import { PackageOrderDetailComponent } from './package-order-detail.component';
import { PackageOrderPopupComponent } from './package-order-dialog.component';
import { PackageOrderDeletePopupComponent } from './package-order-delete-dialog.component';

@Injectable()
export class PackageOrderResolvePagingParams implements Resolve<any> {

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

export const packageOrderRoute: Routes = [
    {
        path: 'package-order',
        component: PackageOrderComponent,
        resolve: {
            'pagingParams': PackageOrderResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PackageOrders'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'package-order/:id',
        component: PackageOrderDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PackageOrders'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const packageOrderPopupRoute: Routes = [
    {
        path: 'package-order-new',
        component: PackageOrderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PackageOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'package-order/:id/edit',
        component: PackageOrderPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PackageOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'package-order/:id/delete',
        component: PackageOrderDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PackageOrders'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
