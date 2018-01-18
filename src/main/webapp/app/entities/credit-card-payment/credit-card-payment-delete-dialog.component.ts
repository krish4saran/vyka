import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CreditCardPayment } from './credit-card-payment.model';
import { CreditCardPaymentPopupService } from './credit-card-payment-popup.service';
import { CreditCardPaymentService } from './credit-card-payment.service';

@Component({
    selector: 'jhi-credit-card-payment-delete-dialog',
    templateUrl: './credit-card-payment-delete-dialog.component.html'
})
export class CreditCardPaymentDeleteDialogComponent {

    creditCardPayment: CreditCardPayment;

    constructor(
        private creditCardPaymentService: CreditCardPaymentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.creditCardPaymentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'creditCardPaymentListModification',
                content: 'Deleted an creditCardPayment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-credit-card-payment-delete-popup',
    template: ''
})
export class CreditCardPaymentDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private creditCardPaymentPopupService: CreditCardPaymentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.creditCardPaymentPopupService
                .open(CreditCardPaymentDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
