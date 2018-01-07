import { BaseEntity } from './../../shared';

export class ClassLength implements BaseEntity {
    constructor(
        public id?: number,
        public classLength?: number,
        public active?: boolean,
        public created?: any,
        public updated?: any,
    ) {
        this.active = false;
    }
}
