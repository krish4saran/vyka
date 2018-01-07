import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Review } from './review.model';
import { ReviewPopupService } from './review-popup.service';
import { ReviewService } from './review.service';
import { ProfileSubject, ProfileSubjectService } from '../profile-subject';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-review-dialog',
    templateUrl: './review-dialog.component.html'
})
export class ReviewDialogComponent implements OnInit {

    review: Review;
    isSaving: boolean;

    profilesubjects: ProfileSubject[];
    createdDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private reviewService: ReviewService,
        private profileSubjectService: ProfileSubjectService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.profileSubjectService.query()
            .subscribe((res: ResponseWrapper) => { this.profilesubjects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.review.id !== undefined) {
            this.subscribeToSaveResponse(
                this.reviewService.update(this.review));
        } else {
            this.subscribeToSaveResponse(
                this.reviewService.create(this.review));
        }
    }

    private subscribeToSaveResponse(result: Observable<Review>) {
        result.subscribe((res: Review) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Review) {
        this.eventManager.broadcast({ name: 'reviewListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProfileSubjectById(index: number, item: ProfileSubject) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-review-popup',
    template: ''
})
export class ReviewPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reviewPopupService: ReviewPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.reviewPopupService
                    .open(ReviewDialogComponent as Component, params['id']);
            } else {
                this.reviewPopupService
                    .open(ReviewDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
