import { ICurso } from 'app/shared/model//curso.model';
import { IAlumno } from 'app/shared/model//alumno.model';

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
  notFinal?: number;
  libro?: string;
  folio?: string;
  estado?: EstadoCursada;
  curso?: ICurso;
  alumno?: IAlumno;
}

export const defaultValue: Readonly<ICursada> = {};
