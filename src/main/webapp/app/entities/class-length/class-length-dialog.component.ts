import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ClassLength } from './class-length.model';
import { ClassLengthPopupService } from './class-length-popup.service';
import { ClassLengthService } from './class-length.service';

@Component({
    selector: 'jhi-class-length-dialog',
    templateUrl: './class-length-dialog.component.html'
})
export class ClassLengthDialogComponent implements OnInit {

    classLength: ClassLength;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private classLengthService: ClassLengthService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.classLength.id !== undefined) {
            this.subscribeToSaveResponse(
                this.classLengthService.update(this.classLength));
        } else {
            this.subscribeToSaveResponse(
                this.classLengthService.create(this.classLength));
        }
    }

    private subscribeToSaveResponse(result: Observable<ClassLength>) {
        result.subscribe((res: ClassLength) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ClassLength) {
        this.eventManager.broadcast({ name: 'classLengthListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-class-length-popup',
    template: ''
})
export class ClassLengthPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private classLengthPopupService: ClassLengthPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.classLengthPopupService
                    .open(ClassLengthDialogComponent as Component, params['id']);
            } else {
                this.classLengthPopupService
                    .open(ClassLengthDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
