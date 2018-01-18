/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { VykaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SettlementDetailComponent } from '../../../../../../main/webapp/app/entities/settlement/settlement-detail.component';
import { SettlementService } from '../../../../../../main/webapp/app/entities/settlement/settlement.service';
import { Settlement } from '../../../../../../main/webapp/app/entities/settlement/settlement.model';

describe('Component Tests', () => {

    describe('Settlement Management Detail Component', () => {
        let comp: SettlementDetailComponent;
        let fixture: ComponentFixture<SettlementDetailComponent>;
        let service: SettlementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VykaTestModule],
                declarations: [SettlementDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SettlementService,
                    JhiEventManager
                ]
            }).overrideTemplate(SettlementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SettlementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SettlementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Settlement(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.settlement).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
