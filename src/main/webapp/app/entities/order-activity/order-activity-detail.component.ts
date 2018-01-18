import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { OrderActivity } from './order-activity.model';
import { OrderActivityService } from './order-activity.service';

@Component({
    selector: 'jhi-order-activity-detail',
    templateUrl: './order-activity-detail.component.html'
})
export class OrderActivityDetailComponent implements OnInit, OnDestroy {

    orderActivity: OrderActivity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private orderActivityService: OrderActivityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInOrderActivities();
    }

    load(id) {
        this.orderActivityService.find(id).subscribe((orderActivity) => {
            this.orderActivity = orderActivity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInOrderActivities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'orderActivityListModification',
            (response) => this.load(this.orderActivity.id)
        );
    }
}
