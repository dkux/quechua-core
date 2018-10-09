import { IMateria } from 'app/shared/model//materia.model';

export interface IDepartamento {
  id?: number;
  nombre?: string;
  codigo?: number;
  materias?: IMateria[];
}

export const defaultValue: Readonly<IDepartamento> = {};
