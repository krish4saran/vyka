/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { VykaTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ClassLengthDetailComponent } from '../../../../../../main/webapp/app/entities/class-length/class-length-detail.component';
import { ClassLengthService } from '../../../../../../main/webapp/app/entities/class-length/class-length.service';
import { ClassLength } from '../../../../../../main/webapp/app/entities/class-length/class-length.model';

describe('Component Tests', () => {

    describe('ClassLength Management Detail Component', () => {
        let comp: ClassLengthDetailComponent;
        let fixture: ComponentFixture<ClassLengthDetailComponent>;
        let service: ClassLengthService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [VykaTestModule],
                declarations: [ClassLengthDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ClassLengthService,
                    JhiEventManager
                ]
            }).overrideTemplate(ClassLengthDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ClassLengthDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ClassLengthService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ClassLength(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.classLength).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
