import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ClassLength } from './class-length.model';
import { ClassLengthService } from './class-length.service';

@Injectable()
export class ClassLengthPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private classLengthService: ClassLengthService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.classLengthService.find(id).subscribe((classLength) => {
                    classLength.created = this.datePipe
                        .transform(classLength.created, 'yyyy-MM-ddTHH:mm:ss');
                    classLength.updated = this.datePipe
                        .transform(classLength.updated, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.classLengthModalRef(component, classLength);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.classLengthModalRef(component, new ClassLength());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    classLengthModalRef(component: Component, classLength: ClassLength): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.classLength = classLength;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
