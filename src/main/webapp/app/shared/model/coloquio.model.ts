import { Moment } from 'moment';
import { ICurso } from 'app/shared/model//curso.model';
import { IPeriodo } from 'app/shared/model//periodo.model';

export const enum Sede {
  PC = 'PC',
  LH = 'LH'
}

export interface IColoquio {
  id?: number;
  aula?: string;
  horaInicio?: string;
  horaFin?: string;
  sede?: Sede;
  fecha?: Moment;
  curso?: ICurso;
  periodo?: IPeriodo;
}

export const defaultValue: Readonly<IColoquio> = {};
