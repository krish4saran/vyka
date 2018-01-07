/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { VykaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ChaptersDetailComponent } from '../../../../../../main/webapp/app/entities/chapters/chapters-detail.component';
import { ChaptersService } from '../../../../../../main/webapp/app/entities/chapters/chapters.service';
import { Chapters } from '../../../../../../main/webapp/app/entities/chapters/chapters.model';

describe('Component Tests', () => {

    describe('Chapters Management Detail Component', () => {
        let comp: ChaptersDetailComponent;
        let fixture: ComponentFixture<ChaptersDetailComponent>;
        let service: ChaptersService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VykaTestModule],
                declarations: [ChaptersDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ChaptersService,
                    JhiEventManager
                ]
            }).overrideTemplate(ChaptersDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ChaptersDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChaptersService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Chapters(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.chapters).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
