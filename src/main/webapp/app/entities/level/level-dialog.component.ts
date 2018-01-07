import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Level } from './level.model';
import { LevelPopupService } from './level-popup.service';
import { LevelService } from './level.service';
import { ProfileSubject, ProfileSubjectService } from '../profile-subject';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-level-dialog',
    templateUrl: './level-dialog.component.html'
})
export class LevelDialogComponent implements OnInit {

    level: Level;
    isSaving: boolean;

    profilesubjects: ProfileSubject[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private levelService: LevelService,
        private profileSubjectService: ProfileSubjectService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.profileSubjectService.query()
            .subscribe((res: ResponseWrapper) => { this.profilesubjects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.level.id !== undefined) {
            this.subscribeToSaveResponse(
                this.levelService.update(this.level));
        } else {
            this.subscribeToSaveResponse(
                this.levelService.create(this.level));
        }
    }

    private subscribeToSaveResponse(result: Observable<Level>) {
        result.subscribe((res: Level) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Level) {
        this.eventManager.broadcast({ name: 'levelListModification', content: 'OK'});
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
    selector: 'jhi-level-popup',
    template: ''
})
export class LevelPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private levelPopupService: LevelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.levelPopupService
                    .open(LevelDialogComponent as Component, params['id']);
            } else {
                this.levelPopupService
                    .open(LevelDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
