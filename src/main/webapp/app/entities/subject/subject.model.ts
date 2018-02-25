import { BaseEntity } from './../../shared';

export class Subject implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public active?: boolean,
        public subjects?: BaseEntity[],
    ) {
        this.active = false;
    }
}
