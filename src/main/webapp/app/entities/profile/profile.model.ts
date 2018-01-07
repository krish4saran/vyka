import { BaseEntity } from './../../shared';

export class Profile implements BaseEntity {
    constructor(
        public id?: number,
        public userId?: number,
        public description?: any,
        public active?: boolean,
        public imageContentType?: string,
        public image?: any,
        public video1ContentType?: string,
        public video1?: any,
        public video2ContentType?: string,
        public video2?: any,
        public backgroundChecked?: boolean,
        public created?: any,
        public updated?: any,
        public locationId?: number,
        public profileSubjects?: BaseEntity[],
        public educations?: BaseEntity[],
        public experiences?: BaseEntity[],
        public awards?: BaseEntity[],
        public availabilities?: BaseEntity[],
        public languages?: BaseEntity[],
    ) {
        this.active = false;
        this.backgroundChecked = false;
    }
}
