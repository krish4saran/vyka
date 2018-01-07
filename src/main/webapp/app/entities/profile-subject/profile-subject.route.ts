import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ProfileSubjectComponent } from './profile-subject.component';
import { ProfileSubjectDetailComponent } from './profile-subject-detail.component';
import { ProfileSubjectPopupComponent } from './profile-subject-dialog.component';
import { ProfileSubjectDeletePopupComponent } from './profile-subject-delete-dialog.component';

@Injectable()
export class ProfileSubjectResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const profileSubjectRoute: Routes = [
    {
        path: 'profile-subject',
        component: ProfileSubjectComponent,
        resolve: {
            'pagingParams': ProfileSubjectResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProfileSubjects'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'profile-subject/:id',
        component: ProfileSubjectDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProfileSubjects'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const profileSubjectPopupRoute: Routes = [
    {
        path: 'profile-subject-new',
        component: ProfileSubjectPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProfileSubjects'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'profile-subject/:id/edit',
        component: ProfileSubjectPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProfileSubjects'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'profile-subject/:id/delete',
        component: ProfileSubjectDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ProfileSubjects'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
