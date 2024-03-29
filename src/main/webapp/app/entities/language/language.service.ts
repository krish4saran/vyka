import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Language } from './language.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class LanguageService {

    private resourceUrl = SERVER_API_URL + 'api/languages';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/languages';

    constructor(private http: Http) { }

    create(language: Language): Observable<Language> {
        const copy = this.convert(language);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(language: Language): Observable<Language> {
        const copy = this.convert(language);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Language> {
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
     * Convert a returned JSON object to Language.
     */
    private convertItemFromServer(json: any): Language {
        const entity: Language = Object.assign(new Language(), json);
        return entity;
    }

    /**
     * Convert a Language to a JSON which can be sent to the server.
     */
    private convert(language: Language): Language {
        const copy: Language = Object.assign({}, language);
        return copy;
    }
}
