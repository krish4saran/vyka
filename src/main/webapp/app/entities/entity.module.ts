import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { VykaProfileModule } from './profile/profile.module';
import { VykaSubjectModule } from './subject/subject.module';
import { VykaProfileSubjectModule } from './profile-subject/profile-subject.module';
import { VykaLevelModule } from './level/level.module';
import { VykaChaptersModule } from './chapters/chapters.module';
import { VykaRateModule } from './rate/rate.module';
import { VykaClassLengthModule } from './class-length/class-length.module';
import { VykaEducationModule } from './education/education.module';
import { VykaExperienceModule } from './experience/experience.module';
import { VykaReviewModule } from './review/review.module';
import { VykaAwardModule } from './award/award.module';
import { VykaLocationModule } from './location/location.module';
import { VykaAvailabilityModule } from './availability/availability.module';
import { VykaLanguageModule } from './language/language.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        VykaProfileModule,
        VykaSubjectModule,
        VykaProfileSubjectModule,
        VykaLevelModule,
        VykaChaptersModule,
        VykaRateModule,
        VykaClassLengthModule,
        VykaEducationModule,
        VykaExperienceModule,
        VykaReviewModule,
        VykaAwardModule,
        VykaLocationModule,
        VykaAvailabilityModule,
        VykaLanguageModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class VykaEntityModule {}
