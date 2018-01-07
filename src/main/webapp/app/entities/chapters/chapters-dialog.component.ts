import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Chapters } from './chapters.model';
import { ChaptersPopupService } from './chapters-popup.service';
import { ChaptersService } from './chapters.service';
import { Level, LevelService } from '../level';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-chapters-dialog',
    templateUrl: './chapters-dialog.component.html'
})
export class ChaptersDialogComponent implements OnInit {

    chapters: Chapters;
    isSaving: boolean;

    levels: Level[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private chaptersService: ChaptersService,
        private levelService: LevelService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.levelService.query()
            .subscribe((res: ResponseWrapper) => { this.levels = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.chapters.id !== undefined) {
            this.subscribeToSaveResponse(
                this.chaptersService.update(this.chapters));
        } else {
            this.subscribeToSaveResponse(
                this.chaptersService.create(this.chapters));
        }
    }

    private subscribeToSaveResponse(result: Observable<Chapters>) {
        result.subscribe((res: Chapters) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Chapters) {
        this.eventManager.broadcast({ name: 'chaptersListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackLevelById(index: number, item: Level) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-chapters-popup',
    template: ''
})
export class ChaptersPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private chaptersPopupService: ChaptersPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.chaptersPopupService
                    .open(ChaptersDialogComponent as Component, params['id']);
            } else {
                this.chaptersPopupService
                    .open(ChaptersDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
