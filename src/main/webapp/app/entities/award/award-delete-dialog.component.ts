import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Award } from './award.model';
import { AwardPopupService } from './award-popup.service';
import { AwardService } from './award.service';

@Component({
    selector: 'jhi-award-delete-dialog',
    templateUrl: './award-delete-dialog.component.html'
})
export class AwardDeleteDialogComponent {

    award: Award;

    constructor(
        private awardService: AwardService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.awardService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'awardListModification',
                content: 'Deleted an award'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-award-delete-popup',
    template: ''
})
export class AwardDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private awardPopupService: AwardPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.awardPopupService
                .open(AwardDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
