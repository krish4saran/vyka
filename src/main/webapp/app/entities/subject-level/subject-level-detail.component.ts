import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { SubjectLevel } from './subject-level.model';
import { SubjectLevelService } from './subject-level.service';

@Component({
    selector: 'jhi-subject-level-detail',
    templateUrl: './subject-level-detail.component.html'
})
export class SubjectLevelDetailComponent implements OnInit, OnDestroy {

    subjectLevel: SubjectLevel;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private subjectLevelService: SubjectLevelService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSubjectLevels();
    }

    load(id) {
        this.subjectLevelService.find(id).subscribe((subjectLevel) => {
            this.subjectLevel = subjectLevel;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSubjectLevels() {
        this.eventSubscriber = this.eventManager.subscribe(
            'subjectLevelListModification',
            (response) => this.load(this.subjectLevel.id)
        );
    }
}
