import { IHorarioCursada } from 'app/shared/model//horario-cursada.model';
import { IProfesor } from 'app/shared/model//profesor.model';
import { IPeriodo } from 'app/shared/model//periodo.model';
import { IMateria } from 'app/shared/model//materia.model';

export const enum CursoEstado {
  ACTIVO = 'ACTIVO',
  INACTIVO = 'INACTIVO',
  ELIMINADO = 'ELIMINADO'
}

export interface ICurso {
  id?: number;
  estado?: CursoEstado;
  vacantes?: number;
  horarios?: IHorarioCursada[];
  profesor?: IProfesor;
  periodo?: IPeriodo;
  materia?: IMateria;
}

export const defaultValue: Readonly<ICurso> = {};
