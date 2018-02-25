import { Component, OnInit } from '@angular/core';
import { Principal, LoginService } from '../../shared/index';
import { SubjectService } from '../subject/subject.service';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { ActivatedRoute, Router } from '@angular/router';
import { Profile } from '../profile/profile.model';
import { ProfileService } from '../profile/profile.service';
import { Observable } from 'rxjs/Observable';
import { Subscription, Subject } from 'rxjs';
import { CreateProfileService } from './create-profile.service';

@Component({
  selector: 'jhi-create-profile',
  templateUrl: './create-profile.component.html',
  styles: []
})
export class CreateProfileComponent implements OnInit {

  profile: Profile;
  userId: number;
  profileSubject = new Subject<Profile>();
  profileSubjectUnavailable = new Subject<Error>();
  constructor(
    private subjectService: SubjectService,
    private jhiAlertService: JhiAlertService,
    private eventManager: JhiEventManager,
    private activatedRoute: ActivatedRoute,
    private principal: Principal,
    private loginService: LoginService,
    private router: Router,
    private profileService: ProfileService,
    private createProfileService: CreateProfileService
  ) { }
   ngOnInit(): void {
    if (!this.principal.isAuthenticated()) {
      this.router.navigate(['register'], { queryParams: { from: 'register-profile' } })
    } else {

      this.activatedRoute.params.subscribe((params) => {
        this.userId = +params['id'];
      });
      this.createProfileService.findProfileByUserId(this.userId);
      this.createProfileService.profileSubject.subscribe( (profile) => {
          this.profile = profile
          this.checkNextAction(profile);
       });
      this.createProfileService.profileSubjectUnavailable.subscribe( (error) => {
        if (this.profile === undefined) {
          this.createProfileService.createProfile(this.userId);
          this.profileSubject.subscribe( (createdProfile) => {
            this.checkNextAction(createdProfile);
          });
        }
      });
      }
  }

  checkNextAction(profile: Profile) {
    if ( profile !== undefined ) {
      if ( profile.profileSubjects === undefined || profile.profileSubjects.length === 0 ) {
        this.router.navigate(['subject'] , {relativeTo: this.activatedRoute});
      } else {
        this.router.navigate(['level'] , {relativeTo: this.activatedRoute});
      }
    }
  }

}
