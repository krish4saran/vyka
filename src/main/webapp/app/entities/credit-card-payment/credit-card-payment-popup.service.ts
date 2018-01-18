import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CreditCardPayment } from './credit-card-payment.model';
import { CreditCardPaymentService } from './credit-card-payment.service';

@Injectable()
export class CreditCardPaymentPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private creditCardPaymentService: CreditCardPaymentService

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
                this.creditCardPaymentService.find(id).subscribe((creditCardPayment) => {
                    this.ngbModalRef = this.creditCardPaymentModalRef(component, creditCardPayment);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.creditCardPaymentModalRef(component, new CreditCardPayment());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    creditCardPaymentModalRef(component: Component, creditCardPayment: CreditCardPayment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.creditCardPayment = creditCardPayment;
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
