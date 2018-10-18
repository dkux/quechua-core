import { ICurso } from 'app/shared/model//curso.model';
import { IPeriodo } from 'app/shared/model//periodo.model';

export const enum Dia {
  LUNES = 'LUNES',
  MARTES = 'MARTES',
  MIERCOLES = 'MIERCOLES',
  JUEVES = 'JUEVES',
  VIERNES = 'VIERNES',
  SABADO = 'SABADO',
  DOMINGO = 'DOMINGO'
}

export const enum Sede {
  PC = 'PC',
  LH = 'LH'
}

export interface IColoquio {
  id?: number;
  dia?: Dia;
  aula?: string;
  horaInicio?: string;
  horaFin?: string;
  sede?: Sede;
  curso?: ICurso;
  periodo?: IPeriodo;
}

export const defaultValue: Readonly<IColoquio> = {};
