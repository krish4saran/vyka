import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CreditCardPaymentComponent } from './credit-card-payment.component';
import { CreditCardPaymentDetailComponent } from './credit-card-payment-detail.component';
import { CreditCardPaymentPopupComponent } from './credit-card-payment-dialog.component';
import { CreditCardPaymentDeletePopupComponent } from './credit-card-payment-delete-dialog.component';

export const creditCardPaymentRoute: Routes = [
    {
        path: 'credit-card-payment',
        component: CreditCardPaymentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditCardPayments'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'credit-card-payment/:id',
        component: CreditCardPaymentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditCardPayments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const creditCardPaymentPopupRoute: Routes = [
    {
        path: 'credit-card-payment-new',
        component: CreditCardPaymentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditCardPayments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'credit-card-payment/:id/edit',
        component: CreditCardPaymentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditCardPayments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'credit-card-payment/:id/delete',
        component: CreditCardPaymentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CreditCardPayments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
