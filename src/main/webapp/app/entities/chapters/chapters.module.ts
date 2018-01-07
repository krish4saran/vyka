import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VykaSharedModule } from '../../shared';
import {
    ChaptersService,
    ChaptersPopupService,
    ChaptersComponent,
    ChaptersDetailComponent,
    ChaptersDialogComponent,
    ChaptersPopupComponent,
    ChaptersDeletePopupComponent,
    ChaptersDeleteDialogComponent,
    chaptersRoute,
    chaptersPopupRoute,
} from './';

const ENTITY_STATES = [
    ...chaptersRoute,
    ...chaptersPopupRoute,
];

@NgModule({
    imports: [
        VykaSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ChaptersComponent,
        ChaptersDetailComponent,
        ChaptersDialogComponent,
        ChaptersDeleteDialogComponent,
        ChaptersPopupComponent,
        ChaptersDeletePopupComponent,
    ],
    entryComponents: [
        ChaptersComponent,
        ChaptersDialogComponent,
        ChaptersPopupComponent,
        ChaptersDeleteDialogComponent,
        ChaptersDeletePopupComponent,
    ],
    providers: [
        ChaptersService,
        ChaptersPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VykaChaptersModule {}
