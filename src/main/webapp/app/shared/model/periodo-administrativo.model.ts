import { Moment } from 'moment';

export interface IPeriodoAdministrativo {
  id?: number;
  fechaInicio?: Moment;
  fechaFin?: Moment;
  actividad?: string;
}

export const defaultValue: Readonly<IPeriodoAdministrativo> = {};
