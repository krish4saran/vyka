import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VykaSharedModule } from '../../shared';
import {
    ScheduleActivityService,
    ScheduleActivityPopupService,
    ScheduleActivityComponent,
    ScheduleActivityDetailComponent,
    ScheduleActivityDialogComponent,
    ScheduleActivityPopupComponent,
    ScheduleActivityDeletePopupComponent,
    ScheduleActivityDeleteDialogComponent,
    scheduleActivityRoute,
    scheduleActivityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...scheduleActivityRoute,
    ...scheduleActivityPopupRoute,
];

@NgModule({
    imports: [
        VykaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ScheduleActivityComponent,
        ScheduleActivityDetailComponent,
        ScheduleActivityDialogComponent,
        ScheduleActivityDeleteDialogComponent,
        ScheduleActivityPopupComponent,
        ScheduleActivityDeletePopupComponent,
    ],
    entryComponents: [
        ScheduleActivityComponent,
        ScheduleActivityDialogComponent,
        ScheduleActivityPopupComponent,
        ScheduleActivityDeleteDialogComponent,
        ScheduleActivityDeletePopupComponent,
    ],
    providers: [
        ScheduleActivityService,
        ScheduleActivityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VykaScheduleActivityModule {}
