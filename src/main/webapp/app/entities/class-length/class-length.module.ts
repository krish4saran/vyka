import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VykaSharedModule } from '../../shared';
import {
    ClassLengthService,
    ClassLengthPopupService,
    ClassLengthComponent,
    ClassLengthDetailComponent,
    ClassLengthDialogComponent,
    ClassLengthPopupComponent,
    ClassLengthDeletePopupComponent,
    ClassLengthDeleteDialogComponent,
    classLengthRoute,
    classLengthPopupRoute,
} from './';

const ENTITY_STATES = [
    ...classLengthRoute,
    ...classLengthPopupRoute,
];

@NgModule({
    imports: [
        VykaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ClassLengthComponent,
        ClassLengthDetailComponent,
        ClassLengthDialogComponent,
        ClassLengthDeleteDialogComponent,
        ClassLengthPopupComponent,
        ClassLengthDeletePopupComponent,
    ],
    entryComponents: [
        ClassLengthComponent,
        ClassLengthDialogComponent,
        ClassLengthPopupComponent,
        ClassLengthDeleteDialogComponent,
        ClassLengthDeletePopupComponent,
    ],
    providers: [
        ClassLengthService,
        ClassLengthPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VykaClassLengthModule {}
