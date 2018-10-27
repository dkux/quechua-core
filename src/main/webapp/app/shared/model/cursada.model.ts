import { ICurso } from 'app/shared/model//curso.model';
import { IAlumno } from 'app/shared/model//alumno.model';
import { IPeriodo } from 'app/shared/model//periodo.model';

export const enum EstadoCursada {
  APROBADO = 'APROBADO',
  REPROBADO = 'REPROBADO',
  FINAL_PENDIENTE = 'FINAL_PENDIENTE',
  FINAL_APROBADO = 'FINAL_APROBADO',
  APLAZO = 'APLAZO'
}

export interface ICursada {
  id?: number;
  notaCursada?: number;
  libro?: string;
  folio?: string;
  estado?: EstadoCursada;
  notaFinal?: number;
  curso?: ICurso;
  alumno?: IAlumno;
  periodo?: IPeriodo;
}

export const defaultValue: Readonly<ICursada> = {};
