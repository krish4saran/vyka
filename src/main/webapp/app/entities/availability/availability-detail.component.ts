import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Availability } from './availability.model';
import { AvailabilityService } from './availability.service';

@Component({
    selector: 'jhi-availability-detail',
    templateUrl: './availability-detail.component.html'
})
export class AvailabilityDetailComponent implements OnInit, OnDestroy {

    availability: Availability;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private availabilityService: AvailabilityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAvailabilities();
    }

    load(id) {
        this.availabilityService.find(id).subscribe((availability) => {
            this.availability = availability;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAvailabilities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'availabilityListModification',
            (response) => this.load(this.availability.id)
        );
    }
}
