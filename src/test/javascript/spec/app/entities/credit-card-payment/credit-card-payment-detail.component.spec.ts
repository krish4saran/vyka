/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { VykaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CreditCardPaymentDetailComponent } from '../../../../../../main/webapp/app/entities/credit-card-payment/credit-card-payment-detail.component';
import { CreditCardPaymentService } from '../../../../../../main/webapp/app/entities/credit-card-payment/credit-card-payment.service';
import { CreditCardPayment } from '../../../../../../main/webapp/app/entities/credit-card-payment/credit-card-payment.model';

describe('Component Tests', () => {

    describe('CreditCardPayment Management Detail Component', () => {
        let comp: CreditCardPaymentDetailComponent;
        let fixture: ComponentFixture<CreditCardPaymentDetailComponent>;
        let service: CreditCardPaymentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VykaTestModule],
                declarations: [CreditCardPaymentDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CreditCardPaymentService,
                    JhiEventManager
                ]
            }).overrideTemplate(CreditCardPaymentDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CreditCardPaymentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreditCardPaymentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CreditCardPayment(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.creditCardPayment).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
