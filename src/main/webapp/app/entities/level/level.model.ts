import { BaseEntity } from './../../shared';

export const enum LevelValue {
    'BEGINNER',
    'INTERMEDIATE',
    'ADVANCED'
}

export class Level implements BaseEntity {
    constructor(
        public id?: number,
        public level?: LevelValue,
        public profileSubjectId?: number,
        public chapters?: BaseEntity[],
    ) {
    }
}
