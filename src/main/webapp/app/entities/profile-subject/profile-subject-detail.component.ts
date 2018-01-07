import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ProfileSubject } from './profile-subject.model';
import { ProfileSubjectService } from './profile-subject.service';

@Component({
    selector: 'jhi-profile-subject-detail',
    templateUrl: './profile-subject-detail.component.html'
})
export class ProfileSubjectDetailComponent implements OnInit, OnDestroy {

    profileSubject: ProfileSubject;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private profileSubjectService: ProfileSubjectService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProfileSubjects();
    }

    load(id) {
        this.profileSubjectService.find(id).subscribe((profileSubject) => {
            this.profileSubject = profileSubject;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProfileSubjects() {
        this.eventSubscriber = this.eventManager.subscribe(
            'profileSubjectListModification',
            (response) => this.load(this.profileSubject.id)
        );
    }
}
