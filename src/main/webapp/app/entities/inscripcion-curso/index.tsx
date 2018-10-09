import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InscripcionCurso from './inscripcion-curso';
import InscripcionCursoDetail from './inscripcion-curso-detail';
import InscripcionCursoUpdate from './inscripcion-curso-update';
import InscripcionCursoDeleteDialog from './inscripcion-curso-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InscripcionCursoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InscripcionCursoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InscripcionCursoDetail} />
      <ErrorBoundaryRoute path={match.url} component={InscripcionCurso} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={InscripcionCursoDeleteDialog} />
  </>
);

export default Routes;
