/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { VykaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AvailabilityDetailComponent } from '../../../../../../main/webapp/app/entities/availability/availability-detail.component';
import { AvailabilityService } from '../../../../../../main/webapp/app/entities/availability/availability.service';
import { Availability } from '../../../../../../main/webapp/app/entities/availability/availability.model';

describe('Component Tests', () => {

    describe('Availability Management Detail Component', () => {
        let comp: AvailabilityDetailComponent;
        let fixture: ComponentFixture<AvailabilityDetailComponent>;
        let service: AvailabilityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VykaTestModule],
                declarations: [AvailabilityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AvailabilityService,
                    JhiEventManager
                ]
            }).overrideTemplate(AvailabilityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AvailabilityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AvailabilityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Availability(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.availability).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
