/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { VykaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EducationDetailComponent } from '../../../../../../main/webapp/app/entities/education/education-detail.component';
import { EducationService } from '../../../../../../main/webapp/app/entities/education/education.service';
import { Education } from '../../../../../../main/webapp/app/entities/education/education.model';

describe('Component Tests', () => {

    describe('Education Management Detail Component', () => {
        let comp: EducationDetailComponent;
        let fixture: ComponentFixture<EducationDetailComponent>;
        let service: EducationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VykaTestModule],
                declarations: [EducationDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EducationService,
                    JhiEventManager
                ]
            }).overrideTemplate(EducationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EducationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EducationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Education(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.education).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
