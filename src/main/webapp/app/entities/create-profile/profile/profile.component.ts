import { Component, OnInit, ElementRef } from '@angular/core';
import { Profile } from '../../profile';
import { ProfileService } from '../../../layouts';
import { CreateProfileService } from '../create-profile.service';
import { ProfileSubject } from '../../profile-subject';
import { Observable } from 'rxjs/Rx';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiDataUtils, JhiAlertService } from 'ng-jhipster';
import { LanguageService, Language } from '../../language';
import { ResponseWrapper } from '../../../shared';

@Component({
    selector: 'jhi-profile',
    templateUrl: './profile.component.html',
    styles: []
})
export class ProfileComponent implements OnInit {

    profile: Profile;
    isSaving: boolean;
    languages: Language[];
    userId: number;
      constructor(
        private profileService: ProfileService,
        private createProfileService: CreateProfileService,
        private route: ActivatedRoute,
        private dataUtils: JhiDataUtils,
        private languageService: LanguageService,
        private elementRef: ElementRef,
        private jhiAlertService: JhiAlertService,
        private router: Router ) {
     }

    ngOnInit(): void {
      this.isSaving = false;
      this.languageService.query()
      .subscribe((res: ResponseWrapper) => { this.languages = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
      this.profile = this.createProfileService.getProfile(this.route);
    }

    save(): void {
        this.isSaving = true;
        this.createProfileService.updateProfile(this.profile);
        this.createProfileService.profileSubject.subscribe( (profile: Profile) => {
          if ( profile !== undefined) {
            this.router.navigate(['profession'] , {relativeTo: this.route.parent});
          }
        });
      }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.profile, this.elementRef, field, fieldContentType, idInput);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
