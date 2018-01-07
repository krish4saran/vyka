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

export const enum TimeZones {
    'IST',
    'CST',
    'PST',
    'EST'
}

export class Availability implements BaseEntity {
    constructor(
        public id?: number,
        public dayOfWeek?: DayOfWeek,
        public availabile?: boolean,
        public timeZone?: TimeZones,
        public profileId?: number,
    ) {
        this.availabile = false;
    }
}
