import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Review } from './review.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ReviewService {

    private resourceUrl = SERVER_API_URL + 'api/reviews';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/reviews';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(review: Review): Observable<Review> {
        const copy = this.convert(review);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(review: Review): Observable<Review> {
        const copy = this.convert(review);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Review> {
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
     * Convert a returned JSON object to Review.
     */
    private convertItemFromServer(json: any): Review {
        const entity: Review = Object.assign(new Review(), json);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a Review to a JSON which can be sent to the server.
     */
    private convert(review: Review): Review {
        const copy: Review = Object.assign({}, review);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(review.createdDate);
        return copy;
    }
}
