import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Carrera from './carrera';
import Departamento from './departamento';
import Materia from './materia';
import HorarioCursada from './horario-cursada';
import Curso from './curso';
import Profesor from './profesor';
import Alumno from './alumno';
import AlumnoCarrera from './alumno-carrera';
import InscripcionCurso from './inscripcion-curso';
import Coloquio from './coloquio';
import Periodo from './periodo';
import InscripcionColoquio from './inscripcion-coloquio';
import PeriodoAdministrativo from './periodo-administrativo';
import Cursada from './cursada';
import Prioridad from './prioridad';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/carrera`} component={Carrera} />
      <ErrorBoundaryRoute path={`${match.url}/departamento`} component={Departamento} />
      <ErrorBoundaryRoute path={`${match.url}/materia`} component={Materia} />
      <ErrorBoundaryRoute path={`${match.url}/horario-cursada`} component={HorarioCursada} />
      <ErrorBoundaryRoute path={`${match.url}/curso`} component={Curso} />
      <ErrorBoundaryRoute path={`${match.url}/profesor`} component={Profesor} />
      <ErrorBoundaryRoute path={`${match.url}/alumno`} component={Alumno} />
      <ErrorBoundaryRoute path={`${match.url}/alumno-carrera`} component={AlumnoCarrera} />
      <ErrorBoundaryRoute path={`${match.url}/inscripcion-curso`} component={InscripcionCurso} />
      <ErrorBoundaryRoute path={`${match.url}/coloquio`} component={Coloquio} />
      <ErrorBoundaryRoute path={`${match.url}/periodo`} component={Periodo} />
      <ErrorBoundaryRoute path={`${match.url}/inscripcion-coloquio`} component={InscripcionColoquio} />
      <ErrorBoundaryRoute path={`${match.url}/periodo-administrativo`} component={PeriodoAdministrativo} />
      <ErrorBoundaryRoute path={`${match.url}/cursada`} component={Cursada} />
      <ErrorBoundaryRoute path={`${match.url}/prioridad`} component={Prioridad} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
