import { Moment } from 'moment';
import { IPeriodo } from 'app/shared/model//periodo.model';

export interface IPrioridad {
  id?: number;
  fecha_habilitacion?: Moment;
  periodo?: IPeriodo;
}

export const defaultValue: Readonly<IPrioridad> = {};
