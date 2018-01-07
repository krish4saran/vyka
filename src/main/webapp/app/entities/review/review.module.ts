import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VykaSharedModule } from '../../shared';
import {
    ReviewService,
    ReviewPopupService,
    ReviewComponent,
    ReviewDetailComponent,
    ReviewDialogComponent,
    ReviewPopupComponent,
    ReviewDeletePopupComponent,
    ReviewDeleteDialogComponent,
    reviewRoute,
    reviewPopupRoute,
    ReviewResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...reviewRoute,
    ...reviewPopupRoute,
];

@NgModule({
    imports: [
        VykaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReviewComponent,
        ReviewDetailComponent,
        ReviewDialogComponent,
        ReviewDeleteDialogComponent,
        ReviewPopupComponent,
        ReviewDeletePopupComponent,
    ],
    entryComponents: [
        ReviewComponent,
        ReviewDialogComponent,
        ReviewPopupComponent,
        ReviewDeleteDialogComponent,
        ReviewDeletePopupComponent,
    ],
    providers: [
        ReviewService,
        ReviewPopupService,
        ReviewResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VykaReviewModule {}
