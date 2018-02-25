import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VykaSharedModule } from '../../shared';
import {
    SubjectLevelService,
    SubjectLevelPopupService,
    SubjectLevelComponent,
    SubjectLevelDetailComponent,
    SubjectLevelDialogComponent,
    SubjectLevelPopupComponent,
    SubjectLevelDeletePopupComponent,
    SubjectLevelDeleteDialogComponent,
    subjectLevelRoute,
    subjectLevelPopupRoute,
} from './';

const ENTITY_STATES = [
    ...subjectLevelRoute,
    ...subjectLevelPopupRoute,
];

@NgModule({
    imports: [
        VykaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SubjectLevelComponent,
        SubjectLevelDetailComponent,
        SubjectLevelDialogComponent,
        SubjectLevelDeleteDialogComponent,
        SubjectLevelPopupComponent,
        SubjectLevelDeletePopupComponent,
    ],
    entryComponents: [
        SubjectLevelComponent,
        SubjectLevelDialogComponent,
        SubjectLevelPopupComponent,
        SubjectLevelDeleteDialogComponent,
        SubjectLevelDeletePopupComponent,
    ],
    providers: [
        SubjectLevelService,
        SubjectLevelPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VykaSubjectLevelModule {}
