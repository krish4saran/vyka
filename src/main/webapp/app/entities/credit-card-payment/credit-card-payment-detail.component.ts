import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CreditCardPayment } from './credit-card-payment.model';
import { CreditCardPaymentService } from './credit-card-payment.service';

@Component({
    selector: 'jhi-credit-card-payment-detail',
    templateUrl: './credit-card-payment-detail.component.html'
})
export class CreditCardPaymentDetailComponent implements OnInit, OnDestroy {

    creditCardPayment: CreditCardPayment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private creditCardPaymentService: CreditCardPaymentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCreditCardPayments();
    }

    load(id) {
        this.creditCardPaymentService.find(id).subscribe((creditCardPayment) => {
            this.creditCardPayment = creditCardPayment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCreditCardPayments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'creditCardPaymentListModification',
            (response) => this.load(this.creditCardPayment.id)
        );
    }
}
