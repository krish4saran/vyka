import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Availability } from './availability.model';
import { AvailabilityService } from './availability.service';

@Injectable()
export class AvailabilityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private availabilityService: AvailabilityService

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
                this.availabilityService.find(id).subscribe((availability) => {
                    availability.effectiveDate = this.datePipe
                        .transform(availability.effectiveDate, 'yyyy-MM-ddTHH:mm:ss');
                    availability.deactivatedDate = this.datePipe
                        .transform(availability.deactivatedDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.availabilityModalRef(component, availability);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.availabilityModalRef(component, new Availability());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    availabilityModalRef(component: Component, availability: Availability): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.availability = availability;
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
