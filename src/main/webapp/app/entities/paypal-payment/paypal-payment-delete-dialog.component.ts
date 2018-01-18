import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PaypalPayment } from './paypal-payment.model';
import { PaypalPaymentPopupService } from './paypal-payment-popup.service';
import { PaypalPaymentService } from './paypal-payment.service';

@Component({
    selector: 'jhi-paypal-payment-delete-dialog',
    templateUrl: './paypal-payment-delete-dialog.component.html'
})
export class PaypalPaymentDeleteDialogComponent {

    paypalPayment: PaypalPayment;

    constructor(
        private paypalPaymentService: PaypalPaymentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.paypalPaymentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'paypalPaymentListModification',
                content: 'Deleted an paypalPayment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-paypal-payment-delete-popup',
    template: ''
})
export class PaypalPaymentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paypalPaymentPopupService: PaypalPaymentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.paypalPaymentPopupService
                .open(PaypalPaymentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
