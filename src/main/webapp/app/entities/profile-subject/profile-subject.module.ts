import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VykaSharedModule } from '../../shared';
import {
    ProfileSubjectService,
    ProfileSubjectPopupService,
    ProfileSubjectComponent,
    ProfileSubjectDetailComponent,
    ProfileSubjectDialogComponent,
    ProfileSubjectPopupComponent,
    ProfileSubjectDeletePopupComponent,
    ProfileSubjectDeleteDialogComponent,
    profileSubjectRoute,
    profileSubjectPopupRoute,
    ProfileSubjectResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...profileSubjectRoute,
    ...profileSubjectPopupRoute,
];

@NgModule({
    imports: [
        VykaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ProfileSubjectComponent,
        ProfileSubjectDetailComponent,
        ProfileSubjectDialogComponent,
        ProfileSubjectDeleteDialogComponent,
        ProfileSubjectPopupComponent,
        ProfileSubjectDeletePopupComponent,
    ],
    entryComponents: [
        ProfileSubjectComponent,
        ProfileSubjectDialogComponent,
        ProfileSubjectPopupComponent,
        ProfileSubjectDeleteDialogComponent,
        ProfileSubjectDeletePopupComponent,
    ],
    providers: [
        ProfileSubjectService,
        ProfileSubjectPopupService,
        ProfileSubjectResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VykaProfileSubjectModule {}
