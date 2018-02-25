import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Account, LoginModalService, Principal } from '../shared';
import { Profile } from '../entities/profile/profile.model';
import { ProfileService } from '../entities/profile/profile.service';
import { Router } from '@angular/router';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    profile: Profile;

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private profileService: ProfileService,
        private router: Router
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
                if ( this.account != null && this.account.profileType ) {
                    this.profileService.find(this.account.id).subscribe((profile) => {
                            this.profile = profile;
                    });
                    // get the user id and route to create-profile to
                    if ( this.profile === undefined ) {
                        this.router.navigate(['/create-profile', this.account.id] );
                    }
                    // create profile using the user id
                    // navigate to create-profile/profile/:id/create
                    // navigate to create-profile/profile/:id/subject/edit
                    // navigate to create-profile/profile/:id/level/edit
                }
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }
}
