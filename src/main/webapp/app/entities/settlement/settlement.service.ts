import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Settlement } from './settlement.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SettlementService {

    private resourceUrl = SERVER_API_URL + 'api/settlements';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/settlements';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(settlement: Settlement): Observable<Settlement> {
        const copy = this.convert(settlement);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(settlement: Settlement): Observable<Settlement> {
        const copy = this.convert(settlement);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Settlement> {
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
     * Convert a returned JSON object to Settlement.
     */
    private convertItemFromServer(json: any): Settlement {
        const entity: Settlement = Object.assign(new Settlement(), json);
        entity.settlementDate = this.dateUtils
            .convertDateTimeFromServer(json.settlementDate);
        return entity;
    }

    /**
     * Convert a Settlement to a JSON which can be sent to the server.
     */
    private convert(settlement: Settlement): Settlement {
        const copy: Settlement = Object.assign({}, settlement);

        copy.settlementDate = this.dateUtils.toDate(settlement.settlementDate);
        return copy;
    }
}
