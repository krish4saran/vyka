import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Chapters } from './chapters.model';
import { ChaptersPopupService } from './chapters-popup.service';
import { ChaptersService } from './chapters.service';

@Component({
    selector: 'jhi-chapters-delete-dialog',
    templateUrl: './chapters-delete-dialog.component.html'
})
export class ChaptersDeleteDialogComponent {

    chapters: Chapters;

    constructor(
        private chaptersService: ChaptersService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.chaptersService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'chaptersListModification',
                content: 'Deleted an chapters'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-chapters-delete-popup',
    template: ''
})
export class ChaptersDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private chaptersPopupService: ChaptersPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.chaptersPopupService
                .open(ChaptersDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
