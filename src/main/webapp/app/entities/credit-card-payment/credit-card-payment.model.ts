import { BaseEntity } from './../../shared';

export const enum CreditCardType {
    'VISA',
    'MASTER_CARD',
    'DISCOVER',
    'AMEX'
}

export class CreditCardPayment implements BaseEntity {
    constructor(
        public id?: number,
        public ccType?: CreditCardType,
        public lastFour?: string,
        public cardName?: string,
        public paymentNumber?: string,
        public paymentId?: number,
    ) {
    }
}
