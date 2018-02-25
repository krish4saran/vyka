import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VykaSharedModule } from '../../shared';
import { VykaAccountModule } from '../../account/account.module';
import { CreateProfileComponent } from './create-profile.component';
import { LevelComponent } from './level/level.component';
import { CreateProfileSubjectComponent } from './subject/subject.component';
import {
  createProfileRoute
  // , CreateProfileResolver
} from './create-profile.route';
import { VykaProfileModule } from '../profile/profile.module';
import { CreateProfileService } from './create-profile.service';

const ENTITY_STATES = [
  ...createProfileRoute
];
@NgModule({
  imports: [
    VykaSharedModule,
    VykaProfileModule,
    RouterModule.forRoot(ENTITY_STATES, { useHash: true })
  ],
  declarations: [
      CreateProfileComponent,
      LevelComponent,
      CreateProfileSubjectComponent
    ],
  providers: [
   // CreateProfileResolver
   CreateProfileService
  ]

})
export class CreateProfileModule { }
