import { BaseEntity } from './../../shared';

export class Chapters implements BaseEntity {
    constructor(
        public id?: number,
        public description?: string,
        public numberOfClasses?: number,
        public levelId?: number,
    ) {
    }
}
