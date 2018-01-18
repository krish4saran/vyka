import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PackageOrder } from './package-order.model';
import { PackageOrderPopupService } from './package-order-popup.service';
import { PackageOrderService } from './package-order.service';

@Component({
    selector: 'jhi-package-order-dialog',
    templateUrl: './package-order-dialog.component.html'
})
export class PackageOrderDialogComponent implements OnInit {

    packageOrder: PackageOrder;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private packageOrderService: PackageOrderService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.packageOrder.id !== undefined) {
            this.subscribeToSaveResponse(
                this.packageOrderService.update(this.packageOrder));
        } else {
            this.subscribeToSaveResponse(
                this.packageOrderService.create(this.packageOrder));
        }
    }

    private subscribeToSaveResponse(result: Observable<PackageOrder>) {
        result.subscribe((res: PackageOrder) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PackageOrder) {
        this.eventManager.broadcast({ name: 'packageOrderListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-package-order-popup',
    template: ''
})
export class PackageOrderPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private packageOrderPopupService: PackageOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.packageOrderPopupService
                    .open(PackageOrderDialogComponent as Component, params['id']);
            } else {
                this.packageOrderPopupService
                    .open(PackageOrderDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
