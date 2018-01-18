import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PackageOrder } from './package-order.model';
import { PackageOrderService } from './package-order.service';

@Injectable()
export class PackageOrderPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private packageOrderService: PackageOrderService

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
                this.packageOrderService.find(id).subscribe((packageOrder) => {
                    packageOrder.createdDate = this.datePipe
                        .transform(packageOrder.createdDate, 'yyyy-MM-ddTHH:mm:ss');
                    packageOrder.updatedDate = this.datePipe
                        .transform(packageOrder.updatedDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.packageOrderModalRef(component, packageOrder);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.packageOrderModalRef(component, new PackageOrder());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    packageOrderModalRef(component: Component, packageOrder: PackageOrder): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.packageOrder = packageOrder;
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
