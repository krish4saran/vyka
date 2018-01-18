import { BaseEntity } from './../../shared';

export const enum PaymentVia {
    'CC',
    'PP'
}

export class Payment implements BaseEntity {
    constructor(
        public id?: number,
        public totalAmount?: number,
        public localCurrencyCode?: string,
        public settlementCurrencyCode?: string,
        public capturedAmount?: number,
        public canceledAmount?: number,
        public refundAmount?: number,
        public paymentVia?: PaymentVia,
        public createdDate?: any,
        public updatedDate?: any,
        public packageOrderId?: number,
        public settlements?: BaseEntity[],
    ) {
    }
}
