import { BaseEntity } from './../../shared';

export class Rate implements BaseEntity {
    constructor(
        public id?: number,
        public rate?: number,
        public created?: any,
        public updated?: any,
        public profileSubjectId?: number,
        public classLengthId?: number,
    ) {
    }
}
