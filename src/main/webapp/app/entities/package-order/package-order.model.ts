import { BaseEntity } from './../../shared';

export const enum OrderStatus {
    'OPEN',
    'CANCELED',
    'COMPLETED',
    'IN_PROGRESS',
    'BOOKED'
}

export class PackageOrder implements BaseEntity {
    constructor(
        public id?: number,
        public rate?: number,
        public totalAmount?: number,
        public quantity?: number,
        public profileSubjectId?: number,
        public status?: OrderStatus,
        public createdDate?: any,
        public updatedDate?: any,
        public studentId?: number,
        public schedules?: BaseEntity[],
        public orderActivities?: BaseEntity[],
    ) {
    }
}
