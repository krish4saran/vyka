import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Settlement } from './settlement.model';
import { SettlementService } from './settlement.service';

@Component({
    selector: 'jhi-settlement-detail',
    templateUrl: './settlement-detail.component.html'
})
export class SettlementDetailComponent implements OnInit, OnDestroy {

    settlement: Settlement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private settlementService: SettlementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSettlements();
    }

    load(id) {
        this.settlementService.find(id).subscribe((settlement) => {
            this.settlement = settlement;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSettlements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'settlementListModification',
            (response) => this.load(this.settlement.id)
        );
    }
}
