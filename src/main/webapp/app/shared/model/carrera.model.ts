import { IMateria } from 'app/shared/model//materia.model';

export interface ICarrera {
  id?: number;
  nombre?: string;
  materias?: IMateria[];
}

export const defaultValue: Readonly<ICarrera> = {};
