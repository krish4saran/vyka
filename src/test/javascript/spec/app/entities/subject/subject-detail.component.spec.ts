/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { VykaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SubjectDetailComponent } from '../../../../../../main/webapp/app/entities/subject/subject-detail.component';
import { SubjectService } from '../../../../../../main/webapp/app/entities/subject/subject.service';
import { Subject } from '../../../../../../main/webapp/app/entities/subject/subject.model';

describe('Component Tests', () => {

    describe('Subject Management Detail Component', () => {
        let comp: SubjectDetailComponent;
        let fixture: ComponentFixture<SubjectDetailComponent>;
        let service: SubjectService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VykaTestModule],
                declarations: [SubjectDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SubjectService,
                    JhiEventManager
                ]
            }).overrideTemplate(SubjectDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SubjectDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubjectService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Subject(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.subject).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
