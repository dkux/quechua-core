export interface IAlumno {
  id?: number;
  nombre?: string;
  apellido?: string;
  padron?: string;
  prioridad?: number;
}

export const defaultValue: Readonly<IAlumno> = {};
