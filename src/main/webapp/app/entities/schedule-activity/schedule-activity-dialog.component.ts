import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ScheduleActivity } from './schedule-activity.model';
import { ScheduleActivityPopupService } from './schedule-activity-popup.service';
import { ScheduleActivityService } from './schedule-activity.service';
import { Schedule, ScheduleService } from '../schedule';
import { OrderActivity, OrderActivityService } from '../order-activity';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-schedule-activity-dialog',
    templateUrl: './schedule-activity-dialog.component.html'
})
export class ScheduleActivityDialogComponent implements OnInit {

    scheduleActivity: ScheduleActivity;
    isSaving: boolean;

    schedules: Schedule[];

    orderactivities: OrderActivity[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private scheduleActivityService: ScheduleActivityService,
        private scheduleService: ScheduleService,
        private orderActivityService: OrderActivityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.scheduleService.query()
            .subscribe((res: ResponseWrapper) => { this.schedules = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.orderActivityService.query()
            .subscribe((res: ResponseWrapper) => { this.orderactivities = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.scheduleActivity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.scheduleActivityService.update(this.scheduleActivity));
        } else {
            this.subscribeToSaveResponse(
                this.scheduleActivityService.create(this.scheduleActivity));
        }
    }

    private subscribeToSaveResponse(result: Observable<ScheduleActivity>) {
        result.subscribe((res: ScheduleActivity) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ScheduleActivity) {
        this.eventManager.broadcast({ name: 'scheduleActivityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackScheduleById(index: number, item: Schedule) {
        return item.id;
    }

    trackOrderActivityById(index: number, item: OrderActivity) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-schedule-activity-popup',
    template: ''
})
export class ScheduleActivityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private scheduleActivityPopupService: ScheduleActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.scheduleActivityPopupService
                    .open(ScheduleActivityDialogComponent as Component, params['id']);
            } else {
                this.scheduleActivityPopupService
                    .open(ScheduleActivityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
