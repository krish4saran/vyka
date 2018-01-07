import { BaseEntity } from './../../shared';

export class Award implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public receivedDate?: any,
        public institute?: string,
        public profileId?: number,
    ) {
    }
}
