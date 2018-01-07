import { BaseEntity } from './../../shared';

export class Review implements BaseEntity {
    constructor(
        public id?: number,
        public rating?: number,
        public comments?: any,
        public createdDate?: any,
        public userId?: number,
        public profileSubjectId?: number,
    ) {
    }
}
