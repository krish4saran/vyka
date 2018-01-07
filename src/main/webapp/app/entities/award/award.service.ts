import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Award } from './award.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AwardService {

    private resourceUrl = SERVER_API_URL + 'api/awards';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/awards';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(award: Award): Observable<Award> {
        const copy = this.convert(award);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(award: Award): Observable<Award> {
        const copy = this.convert(award);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Award> {
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
     * Convert a returned JSON object to Award.
     */
    private convertItemFromServer(json: any): Award {
        const entity: Award = Object.assign(new Award(), json);
        entity.receivedDate = this.dateUtils
            .convertLocalDateFromServer(json.receivedDate);
        return entity;
    }

    /**
     * Convert a Award to a JSON which can be sent to the server.
     */
    private convert(award: Award): Award {
        const copy: Award = Object.assign({}, award);
        copy.receivedDate = this.dateUtils
            .convertLocalDateToServer(award.receivedDate);
        return copy;
    }
}
