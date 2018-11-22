export interface IAlumno {
  id?: number;
  nombre?: string;
  apellido?: string;
  padron?: string;
  prioridad?: number;
  firebaseToken?: string;
}

export const defaultValue: Readonly<IAlumno> = {};
