import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Settlement } from './settlement.model';
import { SettlementPopupService } from './settlement-popup.service';
import { SettlementService } from './settlement.service';
import { Payment, PaymentService } from '../payment';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-settlement-dialog',
    templateUrl: './settlement-dialog.component.html'
})
export class SettlementDialogComponent implements OnInit {

    settlement: Settlement;
    isSaving: boolean;

    payments: Payment[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private settlementService: SettlementService,
        private paymentService: PaymentService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.paymentService.query()
            .subscribe((res: ResponseWrapper) => { this.payments = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.settlement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.settlementService.update(this.settlement));
        } else {
            this.subscribeToSaveResponse(
                this.settlementService.create(this.settlement));
        }
    }

    private subscribeToSaveResponse(result: Observable<Settlement>) {
        result.subscribe((res: Settlement) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Settlement) {
        this.eventManager.broadcast({ name: 'settlementListModification', content: 'OK'});
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
    selector: 'jhi-settlement-popup',
    template: ''
})
export class SettlementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private settlementPopupService: SettlementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.settlementPopupService
                    .open(SettlementDialogComponent as Component, params['id']);
            } else {
                this.settlementPopupService
                    .open(SettlementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
