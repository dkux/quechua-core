import { IAlumno } from 'app/shared/model//alumno.model';
import { ICarrera } from 'app/shared/model//carrera.model';

export interface IAlumnoCarrera {
  id?: number;
  alumno?: IAlumno;
  carrera?: ICarrera;
}

export const defaultValue: Readonly<IAlumnoCarrera> = {};
