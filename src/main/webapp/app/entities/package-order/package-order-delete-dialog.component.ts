import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PackageOrder } from './package-order.model';
import { PackageOrderPopupService } from './package-order-popup.service';
import { PackageOrderService } from './package-order.service';

@Component({
    selector: 'jhi-package-order-delete-dialog',
    templateUrl: './package-order-delete-dialog.component.html'
})
export class PackageOrderDeleteDialogComponent {

    packageOrder: PackageOrder;

    constructor(
        private packageOrderService: PackageOrderService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.packageOrderService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'packageOrderListModification',
                content: 'Deleted an packageOrder'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-package-order-delete-popup',
    template: ''
})
export class PackageOrderDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private packageOrderPopupService: PackageOrderPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.packageOrderPopupService
                .open(PackageOrderDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
