import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VykaSharedModule } from '../../shared';
import {
    PackageOrderService,
    PackageOrderPopupService,
    PackageOrderComponent,
    PackageOrderDetailComponent,
    PackageOrderDialogComponent,
    PackageOrderPopupComponent,
    PackageOrderDeletePopupComponent,
    PackageOrderDeleteDialogComponent,
    packageOrderRoute,
    packageOrderPopupRoute,
    PackageOrderResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...packageOrderRoute,
    ...packageOrderPopupRoute,
];

@NgModule({
    imports: [
        VykaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PackageOrderComponent,
        PackageOrderDetailComponent,
        PackageOrderDialogComponent,
        PackageOrderDeleteDialogComponent,
        PackageOrderPopupComponent,
        PackageOrderDeletePopupComponent,
    ],
    entryComponents: [
        PackageOrderComponent,
        PackageOrderDialogComponent,
        PackageOrderPopupComponent,
        PackageOrderDeleteDialogComponent,
        PackageOrderDeletePopupComponent,
    ],
    providers: [
        PackageOrderService,
        PackageOrderPopupService,
        PackageOrderResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VykaPackageOrderModule {}
