import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PackageOrder } from './package-order.model';
import { PackageOrderService } from './package-order.service';

@Component({
    selector: 'jhi-package-order-detail',
    templateUrl: './package-order-detail.component.html'
})
export class PackageOrderDetailComponent implements OnInit, OnDestroy {

    packageOrder: PackageOrder;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private packageOrderService: PackageOrderService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPackageOrders();
    }

    load(id) {
        this.packageOrderService.find(id).subscribe((packageOrder) => {
            this.packageOrder = packageOrder;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPackageOrders() {
        this.eventSubscriber = this.eventManager.subscribe(
            'packageOrderListModification',
            (response) => this.load(this.packageOrder.id)
        );
    }
}
