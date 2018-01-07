import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VykaSharedModule } from '../../shared';
import {
    AvailabilityService,
    AvailabilityPopupService,
    AvailabilityComponent,
    AvailabilityDetailComponent,
    AvailabilityDialogComponent,
    AvailabilityPopupComponent,
    AvailabilityDeletePopupComponent,
    AvailabilityDeleteDialogComponent,
    availabilityRoute,
    availabilityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...availabilityRoute,
    ...availabilityPopupRoute,
];

@NgModule({
    imports: [
        VykaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AvailabilityComponent,
        AvailabilityDetailComponent,
        AvailabilityDialogComponent,
        AvailabilityDeleteDialogComponent,
        AvailabilityPopupComponent,
        AvailabilityDeletePopupComponent,
    ],
    entryComponents: [
        AvailabilityComponent,
        AvailabilityDialogComponent,
        AvailabilityPopupComponent,
        AvailabilityDeleteDialogComponent,
        AvailabilityDeletePopupComponent,
    ],
    providers: [
        AvailabilityService,
        AvailabilityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VykaAvailabilityModule {}
