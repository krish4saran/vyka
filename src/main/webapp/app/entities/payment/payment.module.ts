import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VykaSharedModule } from '../../shared';
import {
    PaymentService,
    PaymentPopupService,
    PaymentComponent,
    PaymentDetailComponent,
    PaymentDialogComponent,
    PaymentPopupComponent,
    PaymentDeletePopupComponent,
    PaymentDeleteDialogComponent,
    paymentRoute,
    paymentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...paymentRoute,
    ...paymentPopupRoute,
];

@NgModule({
    imports: [
        VykaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PaymentComponent,
        PaymentDetailComponent,
        PaymentDialogComponent,
        PaymentDeleteDialogComponent,
        PaymentPopupComponent,
        PaymentDeletePopupComponent,
    ],
    entryComponents: [
        PaymentComponent,
        PaymentDialogComponent,
        PaymentPopupComponent,
        PaymentDeleteDialogComponent,
        PaymentDeletePopupComponent,
    ],
    providers: [
        PaymentService,
        PaymentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VykaPaymentModule {}
