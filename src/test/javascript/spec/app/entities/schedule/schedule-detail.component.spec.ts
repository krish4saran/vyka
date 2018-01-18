/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { VykaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ScheduleDetailComponent } from '../../../../../../main/webapp/app/entities/schedule/schedule-detail.component';
import { ScheduleService } from '../../../../../../main/webapp/app/entities/schedule/schedule.service';
import { Schedule } from '../../../../../../main/webapp/app/entities/schedule/schedule.model';

describe('Component Tests', () => {

    describe('Schedule Management Detail Component', () => {
        let comp: ScheduleDetailComponent;
        let fixture: ComponentFixture<ScheduleDetailComponent>;
        let service: ScheduleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VykaTestModule],
                declarations: [ScheduleDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ScheduleService,
                    JhiEventManager
                ]
            }).overrideTemplate(ScheduleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ScheduleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ScheduleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Schedule(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.schedule).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
