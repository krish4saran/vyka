
import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, Router } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil, JhiAlertService } from 'ng-jhipster';
import { CreateProfileComponent} from './create-profile.component';
import { CreateProfileSubjectComponent } from './subject/subject.component';
import { LevelComponent} from './level/level.component';
import { ProfileService } from '../profile/profile.service';
import { Profile } from '../profile/profile.model';
import { Observable } from 'rxjs/Observable';
import { ProfileComponent } from './profile/profile.component';
import { ProfessionComponent } from './profession/profession.component';
export const createProfileRoute: Routes = [
    {
        path: 'create-profile/:id',
        component: CreateProfileComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Create Profiles'
        },
        canActivate: [UserRouteAccessService],
        children: [
            {
                path: 'subject',
                component: CreateProfileSubjectComponent
            },
            {
                path: 'level',
                component: LevelComponent
            },
            {
                path: 'profile',
                component: ProfileComponent
            },
            {
                path: 'profession',
                component: ProfessionComponent
            }
        ]
    },
    {
        path: 'create-profile',
        component: CreateProfileComponent
    }
];
