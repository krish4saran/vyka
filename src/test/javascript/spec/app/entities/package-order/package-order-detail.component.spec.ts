/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { VykaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PackageOrderDetailComponent } from '../../../../../../main/webapp/app/entities/package-order/package-order-detail.component';
import { PackageOrderService } from '../../../../../../main/webapp/app/entities/package-order/package-order.service';
import { PackageOrder } from '../../../../../../main/webapp/app/entities/package-order/package-order.model';

describe('Component Tests', () => {

    describe('PackageOrder Management Detail Component', () => {
        let comp: PackageOrderDetailComponent;
        let fixture: ComponentFixture<PackageOrderDetailComponent>;
        let service: PackageOrderService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VykaTestModule],
                declarations: [PackageOrderDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PackageOrderService,
                    JhiEventManager
                ]
            }).overrideTemplate(PackageOrderDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PackageOrderDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PackageOrderService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PackageOrder(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.packageOrder).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
