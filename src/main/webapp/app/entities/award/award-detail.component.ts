import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Award } from './award.model';
import { AwardService } from './award.service';

@Component({
    selector: 'jhi-award-detail',
    templateUrl: './award-detail.component.html'
})
export class AwardDetailComponent implements OnInit, OnDestroy {

    award: Award;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private awardService: AwardService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAwards();
    }

    load(id) {
        this.awardService.find(id).subscribe((award) => {
            this.award = award;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAwards() {
        this.eventSubscriber = this.eventManager.subscribe(
            'awardListModification',
            (response) => this.load(this.award.id)
        );
    }
}
