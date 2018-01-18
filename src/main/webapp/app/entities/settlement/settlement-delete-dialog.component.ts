import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Settlement } from './settlement.model';
import { SettlementPopupService } from './settlement-popup.service';
import { SettlementService } from './settlement.service';

@Component({
    selector: 'jhi-settlement-delete-dialog',
    templateUrl: './settlement-delete-dialog.component.html'
})
export class SettlementDeleteDialogComponent {

    settlement: Settlement;

    constructor(
        private settlementService: SettlementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.settlementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'settlementListModification',
                content: 'Deleted an settlement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-settlement-delete-popup',
    template: ''
})
export class SettlementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private settlementPopupService: SettlementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.settlementPopupService
                .open(SettlementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
