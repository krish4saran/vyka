import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Schedule } from './schedule.model';
import { SchedulePopupService } from './schedule-popup.service';
import { ScheduleService } from './schedule.service';

@Component({
    selector: 'jhi-schedule-delete-dialog',
    templateUrl: './schedule-delete-dialog.component.html'
})
export class ScheduleDeleteDialogComponent {

    schedule: Schedule;

    constructor(
        private scheduleService: ScheduleService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.scheduleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'scheduleListModification',
                content: 'Deleted an schedule'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-schedule-delete-popup',
    template: ''
})
export class ScheduleDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private schedulePopupService: SchedulePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.schedulePopupService
                .open(ScheduleDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
