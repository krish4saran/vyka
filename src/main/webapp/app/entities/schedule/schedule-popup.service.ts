import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Schedule } from './schedule.model';
import { ScheduleService } from './schedule.service';

@Injectable()
export class SchedulePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private scheduleService: ScheduleService

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
                this.scheduleService.find(id).subscribe((schedule) => {
                    schedule.startDate = this.datePipe
                        .transform(schedule.startDate, 'yyyy-MM-ddTHH:mm:ss');
                    schedule.endDate = this.datePipe
                        .transform(schedule.endDate, 'yyyy-MM-ddTHH:mm:ss');
                    schedule.createdDate = this.datePipe
                        .transform(schedule.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                    schedule.updatedDate = this.datePipe
                        .transform(schedule.updatedDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.scheduleModalRef(component, schedule);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.scheduleModalRef(component, new Schedule());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    scheduleModalRef(component: Component, schedule: Schedule): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.schedule = schedule;
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
