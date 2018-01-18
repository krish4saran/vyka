import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Schedule } from './schedule.model';
import { SchedulePopupService } from './schedule-popup.service';
import { ScheduleService } from './schedule.service';
import { PackageOrder, PackageOrderService } from '../package-order';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-schedule-dialog',
    templateUrl: './schedule-dialog.component.html'
})
export class ScheduleDialogComponent implements OnInit {

    schedule: Schedule;
    isSaving: boolean;

    packageorders: PackageOrder[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private scheduleService: ScheduleService,
        private packageOrderService: PackageOrderService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.packageOrderService.query()
            .subscribe((res: ResponseWrapper) => { this.packageorders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.schedule.id !== undefined) {
            this.subscribeToSaveResponse(
                this.scheduleService.update(this.schedule));
        } else {
            this.subscribeToSaveResponse(
                this.scheduleService.create(this.schedule));
        }
    }

    private subscribeToSaveResponse(result: Observable<Schedule>) {
        result.subscribe((res: Schedule) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Schedule) {
        this.eventManager.broadcast({ name: 'scheduleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPackageOrderById(index: number, item: PackageOrder) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-schedule-popup',
    template: ''
})
export class SchedulePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private schedulePopupService: SchedulePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.schedulePopupService
                    .open(ScheduleDialogComponent as Component, params['id']);
            } else {
                this.schedulePopupService
                    .open(ScheduleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
