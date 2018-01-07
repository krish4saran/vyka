import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Subject } from './subject.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SubjectService {

    private resourceUrl = SERVER_API_URL + 'api/subjects';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/subjects';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(subject: Subject): Observable<Subject> {
        const copy = this.convert(subject);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(subject: Subject): Observable<Subject> {
        const copy = this.convert(subject);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Subject> {
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
     * Convert a returned JSON object to Subject.
     */
    private convertItemFromServer(json: any): Subject {
        const entity: Subject = Object.assign(new Subject(), json);
        entity.created = this.dateUtils
            .convertDateTimeFromServer(json.created);
        entity.updated = this.dateUtils
            .convertDateTimeFromServer(json.updated);
        return entity;
    }

    /**
     * Convert a Subject to a JSON which can be sent to the server.
     */
    private convert(subject: Subject): Subject {
        const copy: Subject = Object.assign({}, subject);

        copy.created = this.dateUtils.toDate(subject.created);

        copy.updated = this.dateUtils.toDate(subject.updated);
        return copy;
    }
}
