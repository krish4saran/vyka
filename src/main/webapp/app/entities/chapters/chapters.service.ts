import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Chapters } from './chapters.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ChaptersService {

    private resourceUrl = SERVER_API_URL + 'api/chapters';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/chapters';

    constructor(private http: Http) { }

    create(chapters: Chapters): Observable<Chapters> {
        const copy = this.convert(chapters);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(chapters: Chapters): Observable<Chapters> {
        const copy = this.convert(chapters);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Chapters> {
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
     * Convert a returned JSON object to Chapters.
     */
    private convertItemFromServer(json: any): Chapters {
        const entity: Chapters = Object.assign(new Chapters(), json);
        return entity;
    }

    /**
     * Convert a Chapters to a JSON which can be sent to the server.
     */
    private convert(chapters: Chapters): Chapters {
        const copy: Chapters = Object.assign({}, chapters);
        return copy;
    }
}
