import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { OrderActivity } from './order-activity.model';
import { OrderActivityPopupService } from './order-activity-popup.service';
import { OrderActivityService } from './order-activity.service';

@Component({
    selector: 'jhi-order-activity-delete-dialog',
    templateUrl: './order-activity-delete-dialog.component.html'
})
export class OrderActivityDeleteDialogComponent {

    orderActivity: OrderActivity;

    constructor(
        private orderActivityService: OrderActivityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.orderActivityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'orderActivityListModification',
                content: 'Deleted an orderActivity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-order-activity-delete-popup',
    template: ''
})
export class OrderActivityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderActivityPopupService: OrderActivityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.orderActivityPopupService
                .open(OrderActivityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
