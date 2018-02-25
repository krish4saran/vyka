import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VykaSharedModule } from '../../shared';
import {
    SubjectService,
    SubjectPopupService,
    SubjectComponent,
    SubjectDetailComponent,
    SubjectDialogComponent,
    SubjectPopupComponent,
    SubjectDeletePopupComponent,
    SubjectDeleteDialogComponent,
    subjectRoute,
    subjectPopupRoute,
    SubjectResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...subjectRoute,
    ...subjectPopupRoute,
];

@NgModule({
    imports: [
        VykaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SubjectComponent,
        SubjectDetailComponent,
        SubjectDialogComponent,
        SubjectDeleteDialogComponent,
        SubjectPopupComponent,
        SubjectDeletePopupComponent,
    ],
    entryComponents: [
        SubjectComponent,
        SubjectDialogComponent,
        SubjectPopupComponent,
        SubjectDeleteDialogComponent,
        SubjectDeletePopupComponent,
    ],
    providers: [
        SubjectService,
        SubjectPopupService,
        SubjectResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VykaSubjectModule {}
