import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ScheduleActivity } from './schedule-activity.model';
import { ScheduleActivityPopupService } from './schedule-activity-popup.service';
import { ScheduleActivityService } from './schedule-activity.service';

@Component({
    selector: 'jhi-schedule-activity-delete-dialog',
    templateUrl: './schedule-activity-delete-dialog.component.html'
})
export class ScheduleActivityDeleteDialogComponent {

    scheduleActivity: ScheduleActivity;

    constructor(
        private scheduleActivityService: ScheduleActivityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.scheduleActivityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'scheduleActivityListModification',
                content: 'Deleted an scheduleActivity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-schedule-activity-delete-popup',
    template: ''
})
export class ScheduleActivityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private scheduleActivityPopupService: ScheduleActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.scheduleActivityPopupService
                .open(ScheduleActivityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
