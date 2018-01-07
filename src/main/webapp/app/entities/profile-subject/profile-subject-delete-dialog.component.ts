import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ProfileSubject } from './profile-subject.model';
import { ProfileSubjectPopupService } from './profile-subject-popup.service';
import { ProfileSubjectService } from './profile-subject.service';

@Component({
    selector: 'jhi-profile-subject-delete-dialog',
    templateUrl: './profile-subject-delete-dialog.component.html'
})
export class ProfileSubjectDeleteDialogComponent {

    profileSubject: ProfileSubject;

    constructor(
        private profileSubjectService: ProfileSubjectService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.profileSubjectService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'profileSubjectListModification',
                content: 'Deleted an profileSubject'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-profile-subject-delete-popup',
    template: ''
})
export class ProfileSubjectDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private profileSubjectPopupService: ProfileSubjectPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.profileSubjectPopupService
                .open(ProfileSubjectDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
