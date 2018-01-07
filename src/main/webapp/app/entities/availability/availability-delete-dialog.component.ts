import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Availability } from './availability.model';
import { AvailabilityPopupService } from './availability-popup.service';
import { AvailabilityService } from './availability.service';

@Component({
    selector: 'jhi-availability-delete-dialog',
    templateUrl: './availability-delete-dialog.component.html'
})
export class AvailabilityDeleteDialogComponent {

    availability: Availability;

    constructor(
        private availabilityService: AvailabilityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.availabilityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'availabilityListModification',
                content: 'Deleted an availability'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-availability-delete-popup',
    template: ''
})
export class AvailabilityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private availabilityPopupService: AvailabilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.availabilityPopupService
                .open(AvailabilityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
