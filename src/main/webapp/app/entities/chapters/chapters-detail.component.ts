import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Chapters } from './chapters.model';
import { ChaptersService } from './chapters.service';

@Component({
    selector: 'jhi-chapters-detail',
    templateUrl: './chapters-detail.component.html'
})
export class ChaptersDetailComponent implements OnInit, OnDestroy {

    chapters: Chapters;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private chaptersService: ChaptersService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInChapters();
    }

    load(id) {
        this.chaptersService.find(id).subscribe((chapters) => {
            this.chapters = chapters;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInChapters() {
        this.eventSubscriber = this.eventManager.subscribe(
            'chaptersListModification',
            (response) => this.load(this.chapters.id)
        );
    }
}
