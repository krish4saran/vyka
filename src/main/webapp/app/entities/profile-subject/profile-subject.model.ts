import { BaseEntity } from './../../shared';

export const enum LevelValue {
    'BEGINNER',
    'INTERMEDIATE',
    'ADVANCED'
}

export class ProfileSubject implements BaseEntity {
    constructor(
        public id?: number,
        public level?: LevelValue,
        public rate?: number,
        public sponsored?: boolean,
        public active?: boolean,
        public totalRating?: number,
        public profileId?: number,
        public subjectId?: number,
        public reviews?: BaseEntity[],
    ) {
        this.sponsored = false;
        this.active = false;
    }
}
