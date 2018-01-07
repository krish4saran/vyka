import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Award } from './award.model';
import { AwardService } from './award.service';

@Injectable()
export class AwardPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private awardService: AwardService

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
                this.awardService.find(id).subscribe((award) => {
                    if (award.receivedDate) {
                        award.receivedDate = {
                            year: award.receivedDate.getFullYear(),
                            month: award.receivedDate.getMonth() + 1,
                            day: award.receivedDate.getDate()
                        };
                    }
                    this.ngbModalRef = this.awardModalRef(component, award);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.awardModalRef(component, new Award());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    awardModalRef(component: Component, award: Award): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.award = award;
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
