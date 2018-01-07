import { BaseEntity } from './../../shared';

export class Experience implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public company?: string,
        public begin?: any,
        public end?: any,
        public descriptionContentType?: string,
        public description?: any,
        public profileId?: number,
    ) {
    }
}
