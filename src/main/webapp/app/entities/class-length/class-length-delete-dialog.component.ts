import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ClassLength } from './class-length.model';
import { ClassLengthPopupService } from './class-length-popup.service';
import { ClassLengthService } from './class-length.service';

@Component({
    selector: 'jhi-class-length-delete-dialog',
    templateUrl: './class-length-delete-dialog.component.html'
})
export class ClassLengthDeleteDialogComponent {

    classLength: ClassLength;

    constructor(
        private classLengthService: ClassLengthService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.classLengthService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'classLengthListModification',
                content: 'Deleted an classLength'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-class-length-delete-popup',
    template: ''
})
export class ClassLengthDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private classLengthPopupService: ClassLengthPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.classLengthPopupService
                .open(ClassLengthDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
