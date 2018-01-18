/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { VykaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PaypalPaymentDetailComponent } from '../../../../../../main/webapp/app/entities/paypal-payment/paypal-payment-detail.component';
import { PaypalPaymentService } from '../../../../../../main/webapp/app/entities/paypal-payment/paypal-payment.service';
import { PaypalPayment } from '../../../../../../main/webapp/app/entities/paypal-payment/paypal-payment.model';

describe('Component Tests', () => {

    describe('PaypalPayment Management Detail Component', () => {
        let comp: PaypalPaymentDetailComponent;
        let fixture: ComponentFixture<PaypalPaymentDetailComponent>;
        let service: PaypalPaymentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VykaTestModule],
                declarations: [PaypalPaymentDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PaypalPaymentService,
                    JhiEventManager
                ]
            }).overrideTemplate(PaypalPaymentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaypalPaymentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaypalPaymentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PaypalPayment(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.paypalPayment).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
