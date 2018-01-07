import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VykaSharedModule } from '../../shared';
import {
    AwardService,
    AwardPopupService,
    AwardComponent,
    AwardDetailComponent,
    AwardDialogComponent,
    AwardPopupComponent,
    AwardDeletePopupComponent,
    AwardDeleteDialogComponent,
    awardRoute,
    awardPopupRoute,
} from './';

const ENTITY_STATES = [
    ...awardRoute,
    ...awardPopupRoute,
];

@NgModule({
    imports: [
        VykaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AwardComponent,
        AwardDetailComponent,
        AwardDialogComponent,
        AwardDeleteDialogComponent,
        AwardPopupComponent,
        AwardDeletePopupComponent,
    ],
    entryComponents: [
        AwardComponent,
        AwardDialogComponent,
        AwardPopupComponent,
        AwardDeleteDialogComponent,
        AwardDeletePopupComponent,
    ],
    providers: [
        AwardService,
        AwardPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VykaAwardModule {}
