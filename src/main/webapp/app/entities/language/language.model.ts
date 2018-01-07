import { BaseEntity } from './../../shared';

export const enum LanguageValue {
    'ENGLISH',
    'SPANISH',
    'HINDI',
    'MALAYALAM',
    'KANNADA',
    'TELUGU',
    'ORIYA',
    'MARATHI',
    'URDU',
    'PUNJABI',
    'TAMIL'
}

export class Language implements BaseEntity {
    constructor(
        public id?: number,
        public language?: LanguageValue,
        public profiles?: BaseEntity[],
    ) {
    }
}
