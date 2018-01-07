import { BaseEntity } from './../../shared';

export class Education implements BaseEntity {
    constructor(
        public id?: number,
        public course?: string,
        public university?: string,
        public start?: number,
        public end?: number,
        public profileId?: number,
    ) {
    }
}
