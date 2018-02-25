import { BaseEntity } from './../../shared';

export const enum LevelValue {
    'BEGINNER',
    'INTERMEDIATE',
    'ADVANCED'
}

export class SubjectLevel implements BaseEntity {
    constructor(
        public id?: number,
        public level?: LevelValue,
        public descriptionContentType?: string,
        public description?: any,
        public subjectId?: number,
    ) {
    }
}
