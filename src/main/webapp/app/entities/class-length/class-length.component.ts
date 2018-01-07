import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ClassLength } from './class-length.model';
import { ClassLengthService } from './class-length.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-class-length',
    templateUrl: './class-length.component.html'
})
export class ClassLengthComponent implements OnInit, OnDestroy {
classLengths: ClassLength[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private classLengthService: ClassLengthService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.classLengthService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.classLengths = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.classLengthService.query().subscribe(
            (res: ResponseWrapper) => {
                this.classLengths = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInClassLengths();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ClassLength) {
        return item.id;
    }
    registerChangeInClassLengths() {
        this.eventSubscriber = this.eventManager.subscribe('classLengthListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
