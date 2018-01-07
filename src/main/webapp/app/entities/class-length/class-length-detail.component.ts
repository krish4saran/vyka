import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ClassLength } from './class-length.model';
import { ClassLengthService } from './class-length.service';

@Component({
    selector: 'jhi-class-length-detail',
    templateUrl: './class-length-detail.component.html'
})
export class ClassLengthDetailComponent implements OnInit, OnDestroy {

    classLength: ClassLength;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private classLengthService: ClassLengthService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInClassLengths();
    }

    load(id) {
        this.classLengthService.find(id).subscribe((classLength) => {
            this.classLength = classLength;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInClassLengths() {
        this.eventSubscriber = this.eventManager.subscribe(
            'classLengthListModification',
            (response) => this.load(this.classLength.id)
        );
    }
}
