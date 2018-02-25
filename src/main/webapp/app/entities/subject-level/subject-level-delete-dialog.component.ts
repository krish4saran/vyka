import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SubjectLevel } from './subject-level.model';
import { SubjectLevelPopupService } from './subject-level-popup.service';
import { SubjectLevelService } from './subject-level.service';

@Component({
    selector: 'jhi-subject-level-delete-dialog',
    templateUrl: './subject-level-delete-dialog.component.html'
})
export class SubjectLevelDeleteDialogComponent {

    subjectLevel: SubjectLevel;

    constructor(
        private subjectLevelService: SubjectLevelService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.subjectLevelService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'subjectLevelListModification',
                content: 'Deleted an subjectLevel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-subject-level-delete-popup',
    template: ''
})
export class SubjectLevelDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private subjectLevelPopupService: SubjectLevelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.subjectLevelPopupService
                .open(SubjectLevelDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
