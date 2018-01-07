import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Level } from './level.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LevelService {

    private resourceUrl = SERVER_API_URL + 'api/levels';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/levels';

    constructor(private http: Http) { }

    create(level: Level): Observable<Level> {
        const copy = this.convert(level);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(level: Level): Observable<Level> {
        const copy = this.convert(level);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Level> {
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
     * Convert a returned JSON object to Level.
     */
    private convertItemFromServer(json: any): Level {
        const entity: Level = Object.assign(new Level(), json);
        return entity;
    }

    /**
     * Convert a Level to a JSON which can be sent to the server.
     */
    private convert(level: Level): Level {
        const copy: Level = Object.assign({}, level);
        return copy;
    }
}
