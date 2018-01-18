import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VykaSharedModule } from '../../shared';
import {
    CreditCardPaymentService,
    CreditCardPaymentPopupService,
    CreditCardPaymentComponent,
    CreditCardPaymentDetailComponent,
    CreditCardPaymentDialogComponent,
    CreditCardPaymentPopupComponent,
    CreditCardPaymentDeletePopupComponent,
    CreditCardPaymentDeleteDialogComponent,
    creditCardPaymentRoute,
    creditCardPaymentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...creditCardPaymentRoute,
    ...creditCardPaymentPopupRoute,
];

@NgModule({
    imports: [
        VykaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CreditCardPaymentComponent,
        CreditCardPaymentDetailComponent,
        CreditCardPaymentDialogComponent,
        CreditCardPaymentDeleteDialogComponent,
        CreditCardPaymentPopupComponent,
        CreditCardPaymentDeletePopupComponent,
    ],
    entryComponents: [
        CreditCardPaymentComponent,
        CreditCardPaymentDialogComponent,
        CreditCardPaymentPopupComponent,
        CreditCardPaymentDeleteDialogComponent,
        CreditCardPaymentDeletePopupComponent,
    ],
    providers: [
        CreditCardPaymentService,
        CreditCardPaymentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VykaCreditCardPaymentModule {}
