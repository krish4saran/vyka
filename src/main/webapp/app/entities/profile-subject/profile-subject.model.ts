import { BaseEntity } from './../../shared';

export class ProfileSubject implements BaseEntity {
    constructor(
        public id?: number,
        public profileId?: number,
        public subjectId?: number,
        public reviews?: BaseEntity[],
        public levels?: BaseEntity[],
        public rates?: BaseEntity[],
    ) {
    }
}
