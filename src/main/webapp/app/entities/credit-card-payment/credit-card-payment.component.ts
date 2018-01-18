import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { CreditCardPayment } from './credit-card-payment.model';
import { CreditCardPaymentService } from './credit-card-payment.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-credit-card-payment',
    templateUrl: './credit-card-payment.component.html'
})
export class CreditCardPaymentComponent implements OnInit, OnDestroy {
creditCardPayments: CreditCardPayment[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private creditCardPaymentService: CreditCardPaymentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.creditCardPaymentService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.creditCardPayments = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.creditCardPaymentService.query().subscribe(
            (res: ResponseWrapper) => {
                this.creditCardPayments = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCreditCardPayments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CreditCardPayment) {
        return item.id;
    }
    registerChangeInCreditCardPayments() {
        this.eventSubscriber = this.eventManager.subscribe('creditCardPaymentListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
