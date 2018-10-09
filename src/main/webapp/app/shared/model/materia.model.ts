import { ICurso } from 'app/shared/model//curso.model';
import { IDepartamento } from 'app/shared/model//departamento.model';
import { ICarrera } from 'app/shared/model//carrera.model';

export interface IMateria {
  id?: number;
  nombre?: string;
  codigo?: string;
  creditos?: number;
  cursos?: ICurso[];
  departamento?: IDepartamento;
  carrera?: ICarrera;
}

export const defaultValue: Readonly<IMateria> = {};
