/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { VykaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { OrderActivityDetailComponent } from '../../../../../../main/webapp/app/entities/order-activity/order-activity-detail.component';
import { OrderActivityService } from '../../../../../../main/webapp/app/entities/order-activity/order-activity.service';
import { OrderActivity } from '../../../../../../main/webapp/app/entities/order-activity/order-activity.model';

describe('Component Tests', () => {

    describe('OrderActivity Management Detail Component', () => {
        let comp: OrderActivityDetailComponent;
        let fixture: ComponentFixture<OrderActivityDetailComponent>;
        let service: OrderActivityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VykaTestModule],
                declarations: [OrderActivityDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    OrderActivityService,
                    JhiEventManager
                ]
            }).overrideTemplate(OrderActivityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrderActivityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrderActivityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new OrderActivity(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.orderActivity).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
