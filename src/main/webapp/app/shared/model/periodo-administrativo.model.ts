import { Moment } from 'moment';

export const enum PeriodoActividad {
  CONSULTAR_PRIORIDAD = 'CONSULTAR_PRIORIDAD',
  INSCRIPCION_CURSADA = ' INSCRIPCION_CURSADA',
  INSCRIPCION_COLOQUIO = ' INSCRIPCION_COLOQUIO'
}

export interface IPeriodoAdministrativo {
  id?: number;
  fechaInicio?: Moment;
  fechaFin?: Moment;
  actividad?: PeriodoActividad;
  descripcion?: string;
}

export const defaultValue: Readonly<IPeriodoAdministrativo> = {};
