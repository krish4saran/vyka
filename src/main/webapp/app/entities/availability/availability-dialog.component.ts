import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Availability } from './availability.model';
import { AvailabilityPopupService } from './availability-popup.service';
import { AvailabilityService } from './availability.service';
import { Profile, ProfileService } from '../profile';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-availability-dialog',
    templateUrl: './availability-dialog.component.html'
})
export class AvailabilityDialogComponent implements OnInit {

    availability: Availability;
    isSaving: boolean;

    profiles: Profile[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private availabilityService: AvailabilityService,
        private profileService: ProfileService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.profileService.query()
            .subscribe((res: ResponseWrapper) => { this.profiles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.availability.id !== undefined) {
            this.subscribeToSaveResponse(
                this.availabilityService.update(this.availability));
        } else {
            this.subscribeToSaveResponse(
                this.availabilityService.create(this.availability));
        }
    }

    private subscribeToSaveResponse(result: Observable<Availability>) {
        result.subscribe((res: Availability) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Availability) {
        this.eventManager.broadcast({ name: 'availabilityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackProfileById(index: number, item: Profile) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-availability-popup',
    template: ''
})
export class AvailabilityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private availabilityPopupService: AvailabilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.availabilityPopupService
                    .open(AvailabilityDialogComponent as Component, params['id']);
            } else {
                this.availabilityPopupService
                    .open(AvailabilityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
