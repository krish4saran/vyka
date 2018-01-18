import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { CreditCardPayment } from './credit-card-payment.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CreditCardPaymentService {

    private resourceUrl = SERVER_API_URL + 'api/credit-card-payments';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/credit-card-payments';

    constructor(private http: Http) { }

    create(creditCardPayment: CreditCardPayment): Observable<CreditCardPayment> {
        const copy = this.convert(creditCardPayment);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(creditCardPayment: CreditCardPayment): Observable<CreditCardPayment> {
        const copy = this.convert(creditCardPayment);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CreditCardPayment> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to CreditCardPayment.
     */
    private convertItemFromServer(json: any): CreditCardPayment {
        const entity: CreditCardPayment = Object.assign(new CreditCardPayment(), json);
        return entity;
    }

    /**
     * Convert a CreditCardPayment to a JSON which can be sent to the server.
     */
    private convert(creditCardPayment: CreditCardPayment): CreditCardPayment {
        const copy: CreditCardPayment = Object.assign({}, creditCardPayment);
        return copy;
    }
}
