import { Moment } from 'moment';

export interface IPeriodoAdministrativo {
  id?: number;
  fechaInicio?: Moment;
  fechaFin?: Moment;
  consultarPioridad?: boolean;
  inscribirCursada?: boolean;
  inscribirColoquio?: boolean;
}

export const defaultValue: Readonly<IPeriodoAdministrativo> = {
  consultarPioridad: false,
  inscribirCursada: false,
  inscribirColoquio: false
};
