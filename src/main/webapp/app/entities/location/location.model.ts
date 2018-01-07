import { BaseEntity } from './../../shared';

export class Location implements BaseEntity {
    constructor(
        public id?: number,
        public city?: string,
        public state?: string,
        public country?: string,
    ) {
    }
}
