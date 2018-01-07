/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { VykaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AwardDetailComponent } from '../../../../../../main/webapp/app/entities/award/award-detail.component';
import { AwardService } from '../../../../../../main/webapp/app/entities/award/award.service';
import { Award } from '../../../../../../main/webapp/app/entities/award/award.model';

describe('Component Tests', () => {

    describe('Award Management Detail Component', () => {
        let comp: AwardDetailComponent;
        let fixture: ComponentFixture<AwardDetailComponent>;
        let service: AwardService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VykaTestModule],
                declarations: [AwardDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AwardService,
                    JhiEventManager
                ]
            }).overrideTemplate(AwardDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AwardDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AwardService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Award(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.award).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
