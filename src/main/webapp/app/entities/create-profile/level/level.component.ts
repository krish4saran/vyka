import { Component, OnInit } from '@angular/core';
import { Profile } from '../../profile';
import { ProfileService } from '../../../layouts';
import { CreateProfileService } from '../create-profile.service';
import { ProfileSubject } from '../../profile-subject/profile-subject.model';
import { Observable } from 'rxjs/Rx';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'jhi-level',
  templateUrl: './level.component.html',
  styles: []
})
export class LevelComponent implements OnInit {

  profile: Profile;
  profileSubject: ProfileSubject;
  proxyRate: number;
  isSaving: boolean;
  userId: number;
    constructor(
      private profileService: ProfileService,
      private createProfileService: CreateProfileService,
      private route: ActivatedRoute,
      private router: Router ) {
   }

  ngOnInit(): void {
    this.isSaving = false;
    this.profile = this.createProfileService.getProfile(this.route);
    // if ( this.profile === undefined ) {
    //   this.route.parent.params.subscribe ((params) => {
    //     this.userId = +params['id'];
    //   }
    // )
    // this.createProfileService.findProfileByUserId(this.userId);
    // this.createProfileService.profileSubject.subscribe( (profile) => {
    //   this.profile = profile
    // });
    // }
    if (this.profile !== undefined) {
      const profileSubjectArray = this.profile.profileSubjects;
      if ( profileSubjectArray !== null ) {
        // check if the profile subject is inactive and then use it
        // In the create profile path there should be only one profileSubject
        profileSubjectArray.forEach( (profileSubjectData) => {
          if ( !profileSubjectData.active ) {
            this.profileSubject = profileSubjectData;
          }
        });

        this.proxyRate = (this.profileSubject.rate + (this.profileSubject.rate * 0.25));

    }
  }
  }

  save(): void {
    this.isSaving = true;
    this.createProfileService.updateProfile(this.profile);
    this.createProfileService.profileSubject.subscribe( (profile: Profile) => {
      if ( profile !== undefined) {
        this.router.navigate(['profile'] , {relativeTo: this.route.parent});
      }
    });
  }

  calculateRate(proxyRate: number) {
    let temp: number = proxyRate * 0.25;
    temp += +proxyRate;
    this.profileSubject.rate = temp;
  }
}
