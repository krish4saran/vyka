import { BaseEntity } from './../../shared';

export const enum SettlementType {
    'SETTLED',
    'REFUNDED',
    'VOIDED'
}

export const enum SettlementStatus {
    'NEW',
    'COMPLETED',
    'ERROR',
    'FAILED'
}

export class Settlement implements BaseEntity {
    constructor(
        public id?: number,
        public amount?: number,
        public settlementType?: SettlementType,
        public attempts?: number,
        public status?: SettlementStatus,
        public settlementDate?: any,
        public transactionId?: string,
        public processorResponseCode?: string,
        public processorResponseText?: string,
        public paymentId?: number,
    ) {
    }
}
