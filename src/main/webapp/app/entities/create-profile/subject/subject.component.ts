import { Component, OnInit } from '@angular/core';
import { SubjectService} from '../../subject/subject.service'
import { Principal, ResponseWrapper } from '../../../shared';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';
import { Profile } from '../../profile/profile.model';
import { ProfileSubjectService } from '../../profile-subject/profile-subject.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ProfileSubject } from '../../profile-subject/profile-subject.model';
import { ProfileService } from '../../profile/profile.service';
import { CreateProfileService } from '../create-profile.service';
import { Subject } from '../../subject/subject.model';
@Component({
  selector: 'jhi-subject',
  templateUrl: './subject.component.html',
  styles: []
})
export class CreateProfileSubjectComponent implements OnInit {

  subjects: Subject[];
  profile: Profile;
  profileSubject: ProfileSubject;
  userId: number;
  constructor(
    private subjectService:  SubjectService,
    private jhiAlertService: JhiAlertService,
    private profileSubjectService: ProfileSubjectService,
    private profileService: ProfileService,
    private route: ActivatedRoute,
    private router: Router,
    private createProfileService: CreateProfileService) { }

  ngOnInit() {
    this.loadAll();
     this.route.parent.params.subscribe ((params) => {
      this.userId = +params['id'];
    }
    )
  }

  loadAll() {
     this.subjectService.query().subscribe(
        (res: ResponseWrapper) => {
            this.subjects = res.json;
        },
        (res: ResponseWrapper) => this.onError(res.json)
    );
  }

  private onError(error) {
    this.jhiAlertService.error(error.message, null, null);
  }

  addProfileSubject(id: number) {
      // Get the profile from service
      this.profile = this.createProfileService.getProfile(this.route);
      // create the profile subject
      const profileSubject: ProfileSubject = new ProfileSubject();
      profileSubject.profileId = this.profile.id;
      profileSubject.subjectId = id;
      this.profile.profileSubjects.push(profileSubject);
      this.createProfileService.updateProfile(this.profile);
      // add the profile subject to the profile object saved in the service
      this.createProfileService.profileSubject.subscribe( (profile: Profile) => {
        if ( profile !== undefined) {
          this.router.navigate(['level'] , {relativeTo: this.route.parent});
        }
      });

      this.createProfileService.profileSubjectUnavailable.subscribe( (error: Error) => {
        this.onError(error);
      });
  }
}
