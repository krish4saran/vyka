import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Experience } from './experience.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ExperienceService {

    private resourceUrl = SERVER_API_URL + 'api/experiences';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/experiences';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(experience: Experience): Observable<Experience> {
        const copy = this.convert(experience);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(experience: Experience): Observable<Experience> {
        const copy = this.convert(experience);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Experience> {
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
     * Convert a returned JSON object to Experience.
     */
    private convertItemFromServer(json: any): Experience {
        const entity: Experience = Object.assign(new Experience(), json);
        entity.begin = this.dateUtils
            .convertLocalDateFromServer(json.begin);
        entity.end = this.dateUtils
            .convertLocalDateFromServer(json.end);
        return entity;
    }

    /**
     * Convert a Experience to a JSON which can be sent to the server.
     */
    private convert(experience: Experience): Experience {
        const copy: Experience = Object.assign({}, experience);
        copy.begin = this.dateUtils
            .convertLocalDateToServer(experience.begin);
        copy.end = this.dateUtils
            .convertLocalDateToServer(experience.end);
        return copy;
    }
}
