import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ScheduleActivity } from './schedule-activity.model';
import { ScheduleActivityService } from './schedule-activity.service';

@Component({
    selector: 'jhi-schedule-activity-detail',
    templateUrl: './schedule-activity-detail.component.html'
})
export class ScheduleActivityDetailComponent implements OnInit, OnDestroy {

    scheduleActivity: ScheduleActivity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private scheduleActivityService: ScheduleActivityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInScheduleActivities();
    }

    load(id) {
        this.scheduleActivityService.find(id).subscribe((scheduleActivity) => {
            this.scheduleActivity = scheduleActivity;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInScheduleActivities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'scheduleActivityListModification',
            (response) => this.load(this.scheduleActivity.id)
        );
    }
}
