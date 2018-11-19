import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import carrera, {
  CarreraState
} from 'app/entities/carrera/carrera.reducer';
// prettier-ignore
import departamento, {
  DepartamentoState
} from 'app/entities/departamento/departamento.reducer';
// prettier-ignore
import materia, {
  MateriaState
} from 'app/entities/materia/materia.reducer';
// prettier-ignore
import horarioCursada, {
  HorarioCursadaState
} from 'app/entities/horario-cursada/horario-cursada.reducer';
// prettier-ignore
import curso, {
  CursoState
} from 'app/entities/curso/curso.reducer';
// prettier-ignore
import profesor, {
  ProfesorState
} from 'app/entities/profesor/profesor.reducer';
// prettier-ignore
import alumno, {
  AlumnoState
} from 'app/entities/alumno/alumno.reducer';
// prettier-ignore
import alumnoCarrera, {
  AlumnoCarreraState
} from 'app/entities/alumno-carrera/alumno-carrera.reducer';
// prettier-ignore
import inscripcionCurso, {
  InscripcionCursoState
} from 'app/entities/inscripcion-curso/inscripcion-curso.reducer';
// prettier-ignore
import coloquio, {
  ColoquioState
} from 'app/entities/coloquio/coloquio.reducer';
// prettier-ignore
import periodo, {
  PeriodoState
} from 'app/entities/periodo/periodo.reducer';
// prettier-ignore
import inscripcionColoquio, {
  InscripcionColoquioState
} from 'app/entities/inscripcion-coloquio/inscripcion-coloquio.reducer';
// prettier-ignore
import periodoAdministrativo, {
  PeriodoAdministrativoState
} from 'app/entities/periodo-administrativo/periodo-administrativo.reducer';
// prettier-ignore
import cursada, {
  CursadaState
} from 'app/entities/cursada/cursada.reducer';
// prettier-ignore
import prioridad, {
  PrioridadState
} from 'app/entities/prioridad/prioridad.reducer';
// prettier-ignore
import administradorDepartamento, {
  AdministradorDepartamentoState
} from 'app/entities/administrador-departamento/administrador-departamento.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly carrera: CarreraState;
  readonly departamento: DepartamentoState;
  readonly materia: MateriaState;
  readonly horarioCursada: HorarioCursadaState;
  readonly curso: CursoState;
  readonly profesor: ProfesorState;
  readonly alumno: AlumnoState;
  readonly alumnoCarrera: AlumnoCarreraState;
  readonly inscripcionCurso: InscripcionCursoState;
  readonly coloquio: ColoquioState;
  readonly periodo: PeriodoState;
  readonly inscripcionColoquio: InscripcionColoquioState;
  readonly periodoAdministrativo: PeriodoAdministrativoState;
  readonly cursada: CursadaState;
  readonly prioridad: PrioridadState;
  readonly administradorDepartamento: AdministradorDepartamentoState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  carrera,
  departamento,
  materia,
  horarioCursada,
  curso,
  profesor,
  alumno,
  alumnoCarrera,
  inscripcionCurso,
  coloquio,
  periodo,
  inscripcionColoquio,
  periodoAdministrativo,
  cursada,
  prioridad,
  administradorDepartamento,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
