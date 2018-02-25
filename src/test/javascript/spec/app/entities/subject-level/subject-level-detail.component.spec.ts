/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { VykaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SubjectLevelDetailComponent } from '../../../../../../main/webapp/app/entities/subject-level/subject-level-detail.component';
import { SubjectLevelService } from '../../../../../../main/webapp/app/entities/subject-level/subject-level.service';
import { SubjectLevel } from '../../../../../../main/webapp/app/entities/subject-level/subject-level.model';

describe('Component Tests', () => {

    describe('SubjectLevel Management Detail Component', () => {
        let comp: SubjectLevelDetailComponent;
        let fixture: ComponentFixture<SubjectLevelDetailComponent>;
        let service: SubjectLevelService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VykaTestModule],
                declarations: [SubjectLevelDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SubjectLevelService,
                    JhiEventManager
                ]
            }).overrideTemplate(SubjectLevelDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SubjectLevelDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SubjectLevelService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SubjectLevel(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.subjectLevel).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
