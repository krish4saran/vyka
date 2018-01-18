/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { VykaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ScheduleActivityDetailComponent } from '../../../../../../main/webapp/app/entities/schedule-activity/schedule-activity-detail.component';
import { ScheduleActivityService } from '../../../../../../main/webapp/app/entities/schedule-activity/schedule-activity.service';
import { ScheduleActivity } from '../../../../../../main/webapp/app/entities/schedule-activity/schedule-activity.model';

describe('Component Tests', () => {

    describe('ScheduleActivity Management Detail Component', () => {
        let comp: ScheduleActivityDetailComponent;
        let fixture: ComponentFixture<ScheduleActivityDetailComponent>;
        let service: ScheduleActivityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VykaTestModule],
                declarations: [ScheduleActivityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ScheduleActivityService,
                    JhiEventManager
                ]
            }).overrideTemplate(ScheduleActivityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ScheduleActivityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ScheduleActivityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ScheduleActivity(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.scheduleActivity).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
