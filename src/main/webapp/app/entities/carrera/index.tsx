import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Carrera from './carrera';
import CarreraDetail from './carrera-detail';
import CarreraUpdate from './carrera-update';
import CarreraDeleteDialog from './carrera-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CarreraUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CarreraUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CarreraDetail} />
      <ErrorBoundaryRoute path={match.url} component={Carrera} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CarreraDeleteDialog} />
  </>
);

export default Routes;
