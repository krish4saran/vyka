import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { OrderActivity } from './order-activity.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class OrderActivityService {

    private resourceUrl = SERVER_API_URL + 'api/order-activities';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/order-activities';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(orderActivity: OrderActivity): Observable<OrderActivity> {
        const copy = this.convert(orderActivity);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(orderActivity: OrderActivity): Observable<OrderActivity> {
        const copy = this.convert(orderActivity);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<OrderActivity> {
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
     * Convert a returned JSON object to OrderActivity.
     */
    private convertItemFromServer(json: any): OrderActivity {
        const entity: OrderActivity = Object.assign(new OrderActivity(), json);
        entity.createdDate = this.dateUtils
            .convertDateTimeFromServer(json.createdDate);
        entity.updatedDate = this.dateUtils
            .convertDateTimeFromServer(json.updatedDate);
        return entity;
    }

    /**
     * Convert a OrderActivity to a JSON which can be sent to the server.
     */
    private convert(orderActivity: OrderActivity): OrderActivity {
        const copy: OrderActivity = Object.assign({}, orderActivity);

        copy.createdDate = this.dateUtils.toDate(orderActivity.createdDate);

        copy.updatedDate = this.dateUtils.toDate(orderActivity.updatedDate);
        return copy;
    }
}
