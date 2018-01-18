import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OrderActivity } from './order-activity.model';
import { OrderActivityPopupService } from './order-activity-popup.service';
import { OrderActivityService } from './order-activity.service';
import { PackageOrder, PackageOrderService } from '../package-order';
import { Settlement, SettlementService } from '../settlement';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-order-activity-dialog',
    templateUrl: './order-activity-dialog.component.html'
})
export class OrderActivityDialogComponent implements OnInit {

    orderActivity: OrderActivity;
    isSaving: boolean;

    packageorders: PackageOrder[];

    settlements: Settlement[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private orderActivityService: OrderActivityService,
        private packageOrderService: PackageOrderService,
        private settlementService: SettlementService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.packageOrderService.query()
            .subscribe((res: ResponseWrapper) => { this.packageorders = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.settlementService
            .query({filter: 'orderactivity-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.orderActivity.settlementId) {
                    this.settlements = res.json;
                } else {
                    this.settlementService
                        .find(this.orderActivity.settlementId)
                        .subscribe((subRes: Settlement) => {
                            this.settlements = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.orderActivity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.orderActivityService.update(this.orderActivity));
        } else {
            this.subscribeToSaveResponse(
                this.orderActivityService.create(this.orderActivity));
        }
    }

    private subscribeToSaveResponse(result: Observable<OrderActivity>) {
        result.subscribe((res: OrderActivity) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: OrderActivity) {
        this.eventManager.broadcast({ name: 'orderActivityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPackageOrderById(index: number, item: PackageOrder) {
        return item.id;
    }

    trackSettlementById(index: number, item: Settlement) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-order-activity-popup',
    template: ''
})
export class OrderActivityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderActivityPopupService: OrderActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.orderActivityPopupService
                    .open(OrderActivityDialogComponent as Component, params['id']);
            } else {
                this.orderActivityPopupService
                    .open(OrderActivityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
