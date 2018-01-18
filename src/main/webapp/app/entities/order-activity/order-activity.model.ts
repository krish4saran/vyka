import { BaseEntity } from './../../shared';

export const enum ActivityType {
    'BOOKED',
    'RETURNED',
    'CANCELED'
}

export class OrderActivity implements BaseEntity {
    constructor(
        public id?: number,
        public amount?: number,
        public currencyCode?: string,
        public activityType?: ActivityType,
        public createdDate?: any,
        public updatedDate?: any,
        public amountLocalCurrency?: number,
        public packageOrderId?: number,
        public settlementId?: number,
        public scheduleActivities?: BaseEntity[],
    ) {
    }
}
