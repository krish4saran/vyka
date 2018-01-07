import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ProfileSubject } from './profile-subject.model';
import { ProfileSubjectPopupService } from './profile-subject-popup.service';
import { ProfileSubjectService } from './profile-subject.service';
import { Profile, ProfileService } from '../profile';
import { Subject, SubjectService } from '../subject';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-profile-subject-dialog',
    templateUrl: './profile-subject-dialog.component.html'
})
export class ProfileSubjectDialogComponent implements OnInit {

    profileSubject: ProfileSubject;
    isSaving: boolean;

    profiles: Profile[];

    subjects: Subject[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private profileSubjectService: ProfileSubjectService,
        private profileService: ProfileService,
        private subjectService: SubjectService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.profileService.query()
            .subscribe((res: ResponseWrapper) => { this.profiles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.subjectService
            .query({filter: 'profilesubject-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.profileSubject.subjectId) {
                    this.subjects = res.json;
                } else {
                    this.subjectService
                        .find(this.profileSubject.subjectId)
                        .subscribe((subRes: Subject) => {
                            this.subjects = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.profileSubject.id !== undefined) {
            this.subscribeToSaveResponse(
                this.profileSubjectService.update(this.profileSubject));
        } else {
            this.subscribeToSaveResponse(
                this.profileSubjectService.create(this.profileSubject));
        }
    }

    private subscribeToSaveResponse(result: Observable<ProfileSubject>) {
        result.subscribe((res: ProfileSubject) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ProfileSubject) {
        this.eventManager.broadcast({ name: 'profileSubjectListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProfileById(index: number, item: Profile) {
        return item.id;
    }

    trackSubjectById(index: number, item: Subject) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-profile-subject-popup',
    template: ''
})
export class ProfileSubjectPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private profileSubjectPopupService: ProfileSubjectPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.profileSubjectPopupService
                    .open(ProfileSubjectDialogComponent as Component, params['id']);
            } else {
                this.profileSubjectPopupService
                    .open(ProfileSubjectDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
