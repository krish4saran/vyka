import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Rate } from './rate.model';
import { RatePopupService } from './rate-popup.service';
import { RateService } from './rate.service';
import { ProfileSubject, ProfileSubjectService } from '../profile-subject';
import { ClassLength, ClassLengthService } from '../class-length';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-rate-dialog',
    templateUrl: './rate-dialog.component.html'
})
export class RateDialogComponent implements OnInit {

    rate: Rate;
    isSaving: boolean;

    profilesubjects: ProfileSubject[];

    classlengths: ClassLength[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private rateService: RateService,
        private profileSubjectService: ProfileSubjectService,
        private classLengthService: ClassLengthService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.profileSubjectService.query()
            .subscribe((res: ResponseWrapper) => { this.profilesubjects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.classLengthService.query()
            .subscribe((res: ResponseWrapper) => { this.classlengths = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.rate.id !== undefined) {
            this.subscribeToSaveResponse(
                this.rateService.update(this.rate));
        } else {
            this.subscribeToSaveResponse(
                this.rateService.create(this.rate));
        }
    }

    private subscribeToSaveResponse(result: Observable<Rate>) {
        result.subscribe((res: Rate) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Rate) {
        this.eventManager.broadcast({ name: 'rateListModification', content: 'OK'});
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

    trackClassLengthById(index: number, item: ClassLength) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-rate-popup',
    template: ''
})
export class RatePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ratePopupService: RatePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.ratePopupService
                    .open(RateDialogComponent as Component, params['id']);
            } else {
                this.ratePopupService
                    .open(RateDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
