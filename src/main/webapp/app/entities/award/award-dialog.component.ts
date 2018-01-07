import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Award } from './award.model';
import { AwardPopupService } from './award-popup.service';
import { AwardService } from './award.service';
import { Profile, ProfileService } from '../profile';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-award-dialog',
    templateUrl: './award-dialog.component.html'
})
export class AwardDialogComponent implements OnInit {

    award: Award;
    isSaving: boolean;

    profiles: Profile[];
    receivedDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private awardService: AwardService,
        private profileService: ProfileService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.profileService.query()
            .subscribe((res: ResponseWrapper) => { this.profiles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.award.id !== undefined) {
            this.subscribeToSaveResponse(
                this.awardService.update(this.award));
        } else {
            this.subscribeToSaveResponse(
                this.awardService.create(this.award));
        }
    }

    private subscribeToSaveResponse(result: Observable<Award>) {
        result.subscribe((res: Award) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Award) {
        this.eventManager.broadcast({ name: 'awardListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-award-popup',
    template: ''
})
export class AwardPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private awardPopupService: AwardPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.awardPopupService
                    .open(AwardDialogComponent as Component, params['id']);
            } else {
                this.awardPopupService
                    .open(AwardDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
