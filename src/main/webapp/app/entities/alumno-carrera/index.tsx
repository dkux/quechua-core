import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AlumnoCarrera from './alumno-carrera';
import AlumnoCarreraDetail from './alumno-carrera-detail';
import AlumnoCarreraUpdate from './alumno-carrera-update';
import AlumnoCarreraDeleteDialog from './alumno-carrera-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AlumnoCarreraUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AlumnoCarreraUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AlumnoCarreraDetail} />
      <ErrorBoundaryRoute path={match.url} component={AlumnoCarrera} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={AlumnoCarreraDeleteDialog} />
  </>
);

export default Routes;
