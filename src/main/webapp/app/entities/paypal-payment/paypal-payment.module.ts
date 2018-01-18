import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VykaSharedModule } from '../../shared';
import {
    PaypalPaymentService,
    PaypalPaymentPopupService,
    PaypalPaymentComponent,
    PaypalPaymentDetailComponent,
    PaypalPaymentDialogComponent,
    PaypalPaymentPopupComponent,
    PaypalPaymentDeletePopupComponent,
    PaypalPaymentDeleteDialogComponent,
    paypalPaymentRoute,
    paypalPaymentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...paypalPaymentRoute,
    ...paypalPaymentPopupRoute,
];

@NgModule({
    imports: [
        VykaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PaypalPaymentComponent,
        PaypalPaymentDetailComponent,
        PaypalPaymentDialogComponent,
        PaypalPaymentDeleteDialogComponent,
        PaypalPaymentPopupComponent,
        PaypalPaymentDeletePopupComponent,
    ],
    entryComponents: [
        PaypalPaymentComponent,
        PaypalPaymentDialogComponent,
        PaypalPaymentPopupComponent,
        PaypalPaymentDeleteDialogComponent,
        PaypalPaymentDeletePopupComponent,
    ],
    providers: [
        PaypalPaymentService,
        PaypalPaymentPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VykaPaypalPaymentModule {}
