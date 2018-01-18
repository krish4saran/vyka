import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PaypalPayment } from './paypal-payment.model';
import { PaypalPaymentPopupService } from './paypal-payment-popup.service';
import { PaypalPaymentService } from './paypal-payment.service';
import { Payment, PaymentService } from '../payment';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-paypal-payment-dialog',
    templateUrl: './paypal-payment-dialog.component.html'
})
export class PaypalPaymentDialogComponent implements OnInit {

    paypalPayment: PaypalPayment;
    isSaving: boolean;

    payments: Payment[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private paypalPaymentService: PaypalPaymentService,
        private paymentService: PaymentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.paymentService
            .query({filter: 'paypalpayment-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.paypalPayment.paymentId) {
                    this.payments = res.json;
                } else {
                    this.paymentService
                        .find(this.paypalPayment.paymentId)
                        .subscribe((subRes: Payment) => {
                            this.payments = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.paypalPayment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.paypalPaymentService.update(this.paypalPayment));
        } else {
            this.subscribeToSaveResponse(
                this.paypalPaymentService.create(this.paypalPayment));
        }
    }

    private subscribeToSaveResponse(result: Observable<PaypalPayment>) {
        result.subscribe((res: PaypalPayment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PaypalPayment) {
        this.eventManager.broadcast({ name: 'paypalPaymentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPaymentById(index: number, item: Payment) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-paypal-payment-popup',
    template: ''
})
export class PaypalPaymentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paypalPaymentPopupService: PaypalPaymentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.paypalPaymentPopupService
                    .open(PaypalPaymentDialogComponent as Component, params['id']);
            } else {
                this.paypalPaymentPopupService
                    .open(PaypalPaymentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
