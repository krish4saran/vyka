import { BaseEntity } from './../../shared';

export class PaypalPayment implements BaseEntity {
    constructor(
        public id?: number,
        public paypalPayerId?: string,
        public paypalPayerEmailId?: string,
        public payerFirstName?: string,
        public payerLastName?: string,
        public paymentNumber?: string,
        public paymentId?: number,
    ) {
    }
}
