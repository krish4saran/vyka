import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { OrderActivity } from './order-activity.model';
import { OrderActivityService } from './order-activity.service';

@Injectable()
export class OrderActivityPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private orderActivityService: OrderActivityService

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
                this.orderActivityService.find(id).subscribe((orderActivity) => {
                    orderActivity.createdDate = this.datePipe
                        .transform(orderActivity.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                    orderActivity.updatedDate = this.datePipe
                        .transform(orderActivity.updatedDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.orderActivityModalRef(component, orderActivity);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.orderActivityModalRef(component, new OrderActivity());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    orderActivityModalRef(component: Component, orderActivity: OrderActivity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.orderActivity = orderActivity;
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
