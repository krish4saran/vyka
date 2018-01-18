import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CreditCardPayment } from './credit-card-payment.model';
import { CreditCardPaymentPopupService } from './credit-card-payment-popup.service';
import { CreditCardPaymentService } from './credit-card-payment.service';
import { Payment, PaymentService } from '../payment';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-credit-card-payment-dialog',
    templateUrl: './credit-card-payment-dialog.component.html'
})
export class CreditCardPaymentDialogComponent implements OnInit {

    creditCardPayment: CreditCardPayment;
    isSaving: boolean;

    payments: Payment[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private creditCardPaymentService: CreditCardPaymentService,
        private paymentService: PaymentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.paymentService
            .query({filter: 'creditcardpayment-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.creditCardPayment.paymentId) {
                    this.payments = res.json;
                } else {
                    this.paymentService
                        .find(this.creditCardPayment.paymentId)
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
        if (this.creditCardPayment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.creditCardPaymentService.update(this.creditCardPayment));
        } else {
            this.subscribeToSaveResponse(
                this.creditCardPaymentService.create(this.creditCardPayment));
        }
    }

    private subscribeToSaveResponse(result: Observable<CreditCardPayment>) {
        result.subscribe((res: CreditCardPayment) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CreditCardPayment) {
        this.eventManager.broadcast({ name: 'creditCardPaymentListModification', content: 'OK'});
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
    selector: 'jhi-credit-card-payment-popup',
    template: ''
})
export class CreditCardPaymentPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private creditCardPaymentPopupService: CreditCardPaymentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.creditCardPaymentPopupService
                    .open(CreditCardPaymentDialogComponent as Component, params['id']);
            } else {
                this.creditCardPaymentPopupService
                    .open(CreditCardPaymentDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
