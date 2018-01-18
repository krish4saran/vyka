import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VykaSharedModule } from '../../shared';
import {
    OrderActivityService,
    OrderActivityPopupService,
    OrderActivityComponent,
    OrderActivityDetailComponent,
    OrderActivityDialogComponent,
    OrderActivityPopupComponent,
    OrderActivityDeletePopupComponent,
    OrderActivityDeleteDialogComponent,
    orderActivityRoute,
    orderActivityPopupRoute,
    OrderActivityResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...orderActivityRoute,
    ...orderActivityPopupRoute,
];

@NgModule({
    imports: [
        VykaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        OrderActivityComponent,
        OrderActivityDetailComponent,
        OrderActivityDialogComponent,
        OrderActivityDeleteDialogComponent,
        OrderActivityPopupComponent,
        OrderActivityDeletePopupComponent,
    ],
    entryComponents: [
        OrderActivityComponent,
        OrderActivityDialogComponent,
        OrderActivityPopupComponent,
        OrderActivityDeleteDialogComponent,
        OrderActivityDeletePopupComponent,
    ],
    providers: [
        OrderActivityService,
        OrderActivityPopupService,
        OrderActivityResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VykaOrderActivityModule {}
