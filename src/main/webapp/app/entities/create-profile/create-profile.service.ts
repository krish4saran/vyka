import { Injectable } from '@angular/core';
import { Subject } from 'rxjs'
import { Profile } from '../profile/profile.model';
import { ProfileService } from '../profile/profile.service';
import { JhiAlertService } from 'ng-jhipster';
import { ProfileSubject } from '../profile-subject/profile-subject.model';
import { ProfileSubjectService } from '../profile-subject/profile-subject.service';
import { ActivatedRoute } from '@angular/router';

@Injectable()
export class CreateProfileService {
    profileSubject = new Subject<Profile>();
    profileSubjectUnavailable = new Subject<Error>();
    profile: Profile;

    constructor(
       private profileService: ProfileService,
        private jhiAlertService: JhiAlertService,
        private profileSubjectService: ProfileSubjectService) { }

    findProfileByUserId(userId: number) {
        this.profileService.findByUserId(userId).subscribe(
         (profile: Profile) => {
             this.profile =  profile;
             this.profileSubject.next(profile);
         },
         (error) => {
           this.profileSubjectUnavailable.next(error);
         }
       );
     }

     public getProfile(route: ActivatedRoute): Profile {
       let userId = null;
      if ( this.profile === undefined ) {
          route.parent.params.subscribe ((params) => {
          userId = +params['id'];
        }
      )
      this.findProfileByUserId(userId);
      this.profileSubject.subscribe( (profile) => {
        this.profile = profile
      });
      }
      return this.profile;
    }

     createProfile(userId: number) {
        const profileObj: Profile = new Profile();
        profileObj.userId = userId;
        profileObj.description = ' ';
        this.profileService.create(profileObj).subscribe(
          (res: Profile) => {
            this.profile = res;
            this.profileSubject.next(res);
          },
          (error) => this.jhiAlertService.error(error.message, null, null)
        );
      }

      updateProfile(profile: Profile) {

        this.profileService.update(profile).subscribe(
          (updatedProfile: Profile) => {
            this.profileSubject.next(updatedProfile);
          },
          (error) => {
            this.profileSubjectUnavailable.next(error);
          }
        )
      }
}
