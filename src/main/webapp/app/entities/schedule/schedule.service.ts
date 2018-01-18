import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Schedule } from './schedule.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ScheduleService {

    private resourceUrl = SERVER_API_URL + 'api/schedules';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/schedules';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(schedule: Schedule): Observable<Schedule> {
        const copy = this.convert(schedule);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(schedule: Schedule): Observable<Schedule> {
        const copy = this.convert(schedule);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Schedule> {
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
     * Convert a returned JSON object to Schedule.
     */
    private convertItemFromServer(json: any): Schedule {
        const entity: Schedule = Object.assign(new Schedule(), json);
        entity.startDate = this.dateUtils
            .convertDateTimeFromServer(json.startDate);
        entity.endDate = this.dateUtils
            .convertDateTimeFromServer(json.endDate);
        entity.createdDate = this.dateUtils
            .convertDateTimeFromServer(json.createdDate);
        entity.updatedDate = this.dateUtils
            .convertDateTimeFromServer(json.updatedDate);
        return entity;
    }

    /**
     * Convert a Schedule to a JSON which can be sent to the server.
     */
    private convert(schedule: Schedule): Schedule {
        const copy: Schedule = Object.assign({}, schedule);

        copy.startDate = this.dateUtils.toDate(schedule.startDate);

        copy.endDate = this.dateUtils.toDate(schedule.endDate);

        copy.createdDate = this.dateUtils.toDate(schedule.createdDate);

        copy.updatedDate = this.dateUtils.toDate(schedule.updatedDate);
        return copy;
    }
}
