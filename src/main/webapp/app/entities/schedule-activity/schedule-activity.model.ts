import { BaseEntity } from './../../shared';

export class ScheduleActivity implements BaseEntity {
    constructor(
        public id?: number,
        public amount?: number,
        public createdDate?: any,
        public updatedDate?: any,
        public scheduleId?: number,
        public orderActivityId?: number,
    ) {
    }
}
