import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { SubjectLevel } from './subject-level.model';
import { SubjectLevelPopupService } from './subject-level-popup.service';
import { SubjectLevelService } from './subject-level.service';
import { Subject, SubjectService } from '../subject';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-subject-level-dialog',
    templateUrl: './subject-level-dialog.component.html'
})
export class SubjectLevelDialogComponent implements OnInit {

    subjectLevel: SubjectLevel;
    isSaving: boolean;

    subjects: Subject[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private subjectLevelService: SubjectLevelService,
        private subjectService: SubjectService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.subjectService.query()
            .subscribe((res: ResponseWrapper) => { this.subjects = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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
        if (this.subjectLevel.id !== undefined) {
            this.subscribeToSaveResponse(
                this.subjectLevelService.update(this.subjectLevel));
        } else {
            this.subscribeToSaveResponse(
                this.subjectLevelService.create(this.subjectLevel));
        }
    }

    private subscribeToSaveResponse(result: Observable<SubjectLevel>) {
        result.subscribe((res: SubjectLevel) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: SubjectLevel) {
        this.eventManager.broadcast({ name: 'subjectLevelListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackSubjectById(index: number, item: Subject) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-subject-level-popup',
    template: ''
})
export class SubjectLevelPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private subjectLevelPopupService: SubjectLevelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.subjectLevelPopupService
                    .open(SubjectLevelDialogComponent as Component, params['id']);
            } else {
                this.subjectLevelPopupService
                    .open(SubjectLevelDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
