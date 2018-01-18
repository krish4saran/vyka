import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SettlementComponent } from './settlement.component';
import { SettlementDetailComponent } from './settlement-detail.component';
import { SettlementPopupComponent } from './settlement-dialog.component';
import { SettlementDeletePopupComponent } from './settlement-delete-dialog.component';

export const settlementRoute: Routes = [
    {
        path: 'settlement',
        component: SettlementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Settlements'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'settlement/:id',
        component: SettlementDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Settlements'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const settlementPopupRoute: Routes = [
    {
        path: 'settlement-new',
        component: SettlementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Settlements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'settlement/:id/edit',
        component: SettlementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Settlements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'settlement/:id/delete',
        component: SettlementDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Settlements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
