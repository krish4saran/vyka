/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { VykaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ProfileSubjectDetailComponent } from '../../../../../../main/webapp/app/entities/profile-subject/profile-subject-detail.component';
import { ProfileSubjectService } from '../../../../../../main/webapp/app/entities/profile-subject/profile-subject.service';
import { ProfileSubject } from '../../../../../../main/webapp/app/entities/profile-subject/profile-subject.model';

describe('Component Tests', () => {

    describe('ProfileSubject Management Detail Component', () => {
        let comp: ProfileSubjectDetailComponent;
        let fixture: ComponentFixture<ProfileSubjectDetailComponent>;
        let service: ProfileSubjectService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VykaTestModule],
                declarations: [ProfileSubjectDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ProfileSubjectService,
                    JhiEventManager
                ]
            }).overrideTemplate(ProfileSubjectDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProfileSubjectDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProfileSubjectService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ProfileSubject(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.profileSubject).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
