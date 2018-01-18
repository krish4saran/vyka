import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VykaSharedModule } from '../../shared';
import {
    ScheduleService,
    SchedulePopupService,
    ScheduleComponent,
    ScheduleDetailComponent,
    ScheduleDialogComponent,
    SchedulePopupComponent,
    ScheduleDeletePopupComponent,
    ScheduleDeleteDialogComponent,
    scheduleRoute,
    schedulePopupRoute,
    ScheduleResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...scheduleRoute,
    ...schedulePopupRoute,
];

@NgModule({
    imports: [
        VykaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ScheduleComponent,
        ScheduleDetailComponent,
        ScheduleDialogComponent,
        ScheduleDeleteDialogComponent,
        SchedulePopupComponent,
        ScheduleDeletePopupComponent,
    ],
    entryComponents: [
        ScheduleComponent,
        ScheduleDialogComponent,
        SchedulePopupComponent,
        ScheduleDeleteDialogComponent,
        ScheduleDeletePopupComponent,
    ],
    providers: [
        ScheduleService,
        SchedulePopupService,
        ScheduleResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VykaScheduleModule {}
