import { IColoquio } from 'app/shared/model//coloquio.model';
import { IAlumno } from 'app/shared/model//alumno.model';
import { ICursada } from 'app/shared/model//cursada.model';

export const enum InscripcionColoquioEstado {
  ACTIVA = 'ACTIVA',
  ELIMINADA = 'ELIMINADA',
  APROBADA = 'APROBADA',
  DESAPROBADA = 'DESAPROBADA'
}

export interface IInscripcionColoquio {
  id?: number;
  estado?: InscripcionColoquioEstado;
  coloquio?: IColoquio;
  alumno?: IAlumno;
  cursada?: ICursada;
}

export const defaultValue: Readonly<IInscripcionColoquio> = {};
