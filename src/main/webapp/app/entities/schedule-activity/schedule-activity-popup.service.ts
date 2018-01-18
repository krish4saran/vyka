import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ScheduleActivity } from './schedule-activity.model';
import { ScheduleActivityService } from './schedule-activity.service';

@Injectable()
export class ScheduleActivityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private scheduleActivityService: ScheduleActivityService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.scheduleActivityService.find(id).subscribe((scheduleActivity) => {
                    scheduleActivity.createdDate = this.datePipe
                        .transform(scheduleActivity.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                    scheduleActivity.updatedDate = this.datePipe
                        .transform(scheduleActivity.updatedDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.scheduleActivityModalRef(component, scheduleActivity);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.scheduleActivityModalRef(component, new ScheduleActivity());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    scheduleActivityModalRef(component: Component, scheduleActivity: ScheduleActivity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.scheduleActivity = scheduleActivity;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
