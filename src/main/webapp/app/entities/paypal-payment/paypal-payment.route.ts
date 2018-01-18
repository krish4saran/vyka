import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PaypalPaymentComponent } from './paypal-payment.component';
import { PaypalPaymentDetailComponent } from './paypal-payment-detail.component';
import { PaypalPaymentPopupComponent } from './paypal-payment-dialog.component';
import { PaypalPaymentDeletePopupComponent } from './paypal-payment-delete-dialog.component';

export const paypalPaymentRoute: Routes = [
    {
        path: 'paypal-payment',
        component: PaypalPaymentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaypalPayments'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'paypal-payment/:id',
        component: PaypalPaymentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaypalPayments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paypalPaymentPopupRoute: Routes = [
    {
        path: 'paypal-payment-new',
        component: PaypalPaymentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaypalPayments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'paypal-payment/:id/edit',
        component: PaypalPaymentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaypalPayments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'paypal-payment/:id/delete',
        component: PaypalPaymentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PaypalPayments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
