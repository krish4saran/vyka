import { BaseEntity } from './../../shared';

export const enum ScheduleStatus {
    'OPEN',
    'BOOKED',
    'CANCELED',
    'COMPLETED',
    'IN_PROGRESS',
    'RESCHEDULED',
    'TECHNICAL_ISSUE',
    'DID_NOT_SHOW_UP'
}

export class Schedule implements BaseEntity {
    constructor(
        public id?: number,
        public availabilityId?: number,
        public startDate?: any,
        public endDate?: any,
        public status?: ScheduleStatus,
        public createdDate?: any,
        public updatedDate?: any,
        public amount?: number,
        public packageOrderId?: number,
        public scheduleActivities?: BaseEntity[],
    ) {
    }
}
