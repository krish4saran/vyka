import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PaypalPayment } from './paypal-payment.model';
import { PaypalPaymentService } from './paypal-payment.service';

@Component({
    selector: 'jhi-paypal-payment-detail',
    templateUrl: './paypal-payment-detail.component.html'
})
export class PaypalPaymentDetailComponent implements OnInit, OnDestroy {

    paypalPayment: PaypalPayment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private paypalPaymentService: PaypalPaymentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPaypalPayments();
    }

    load(id) {
        this.paypalPaymentService.find(id).subscribe((paypalPayment) => {
            this.paypalPayment = paypalPayment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPaypalPayments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'paypalPaymentListModification',
            (response) => this.load(this.paypalPayment.id)
        );
    }
}
