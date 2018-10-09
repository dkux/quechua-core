import { IAlumno } from 'app/shared/model//alumno.model';
import { ICurso } from 'app/shared/model//curso.model';

export const enum InscripcionCursoEstado {
  REGULAR = 'REGULAR',
  CONDICIONAL = 'CONDICIONAL',
  ELIMINADA = 'ELIMINADA'
}

export const enum CursadaEstado {
  APROBADA = 'APROBADA',
  DESAPROBADA = 'DESAPROBADA'
}

export interface IInscripcionCurso {
  id?: number;
  estado?: InscripcionCursoEstado;
  cursadaEstado?: CursadaEstado;
  alumno?: IAlumno;
  curso?: ICurso;
}

export const defaultValue: Readonly<IInscripcionCurso> = {};
