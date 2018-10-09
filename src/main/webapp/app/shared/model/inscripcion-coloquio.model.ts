import { IColoquio } from 'app/shared/model//coloquio.model';
import { IAlumno } from 'app/shared/model//alumno.model';

export const enum InscripcionColoquioEstado {
  ACTIVA = 'ACTIVA',
  ELIMINADA = 'ELIMINADA'
}

export interface IInscripcionColoquio {
  id?: number;
  estado?: InscripcionColoquioEstado;
  coloquio?: IColoquio;
  alumno?: IAlumno;
}

export const defaultValue: Readonly<IInscripcionColoquio> = {};
