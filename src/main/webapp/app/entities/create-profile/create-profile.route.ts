
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

// @Injectable()
// export class CreateProfileResolver implements Resolve<Profile> {
//     profile: Profile;
//     constructor(private profileService: ProfileService,
//                 private router: Router,
//                 private jhiAlertService: JhiAlertService ) {}

//     resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Profile> {
//         let userId = route.params['id'];

//         if ( userId === undefined || userId === null) {
//             return Observable.of(null);
//         }
//         // if the id is not a number then send the user to home page
//         if ( isNaN(+userId) ) {
//             this.jhiAlertService.error('invalid user id', null, null);
//             this.router.navigate(['/home']);
//             return Observable.of(null);
//         }

//         userId = +userId;
//         this.profile = this.findProfileByUserId(userId);

//         // if still profile is not created
//         if ( this.profile === null || this.profile === undefined ) {
//             this.createProfile(userId);
//             this.profile = this.findProfileByUserId(userId);
//         }

//          return Observable.of(this.profile);
//     }

//     createProfile(userId: number): Profile {
//         this.profile = new Profile();
//         this.profile.userId = userId;
//         this.profile.description = ' ';
//         this.profileService.create(this.profile).subscribe( (profile) => {
//           this.profile = profile;
//         });
//         return this.profile;
//     }

//     findProfileByUserId(userId: number): Profile {
//         this.profileService.findByUserId(userId).subscribe((profile) => {
//             if ( profile === null || profile === undefined ) {
//                 this.profile = this.createProfile(userId);
//             }else {
//                 this.profile = profile;
//             }
//         });
//         return this.profile;
//     }
// }

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
            }
        ]
    },
    {
        path: 'create-profile',
        component: CreateProfileComponent
    }
];
