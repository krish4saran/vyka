import { BaseEntity } from './../../shared';

export const enum DayOfWeek {
    'SUN',
    'MON',
    'TUE',
    'WED',
    'THU',
    'FRI',
    'SAT'
}

export class Availability implements BaseEntity {
    constructor(
        public id?: number,
        public dayOfWeek?: DayOfWeek,
        public booked?: boolean,
        public active?: boolean,
        public effectiveDate?: any,
        public deactivatedDate?: any,
        public profileId?: number,
    ) {
        this.booked = false;
        this.active = false;
    }
}
