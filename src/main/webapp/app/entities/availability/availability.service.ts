import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Availability } from './availability.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AvailabilityService {

    private resourceUrl = SERVER_API_URL + 'api/availabilities';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/availabilities';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(availability: Availability): Observable<Availability> {
        const copy = this.convert(availability);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(availability: Availability): Observable<Availability> {
        const copy = this.convert(availability);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Availability> {
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
     * Convert a returned JSON object to Availability.
     */
    private convertItemFromServer(json: any): Availability {
        const entity: Availability = Object.assign(new Availability(), json);
        entity.effectiveDate = this.dateUtils
            .convertDateTimeFromServer(json.effectiveDate);
        entity.deactivatedDate = this.dateUtils
            .convertDateTimeFromServer(json.deactivatedDate);
        return entity;
    }

    /**
     * Convert a Availability to a JSON which can be sent to the server.
     */
    private convert(availability: Availability): Availability {
        const copy: Availability = Object.assign({}, availability);

        copy.effectiveDate = this.dateUtils.toDate(availability.effectiveDate);

        copy.deactivatedDate = this.dateUtils.toDate(availability.deactivatedDate);
        return copy;
    }
}
