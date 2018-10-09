import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import HorarioCursada from './horario-cursada';
import HorarioCursadaDetail from './horario-cursada-detail';
import HorarioCursadaUpdate from './horario-cursada-update';
import HorarioCursadaDeleteDialog from './horario-cursada-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={HorarioCursadaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={HorarioCursadaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={HorarioCursadaDetail} />
      <ErrorBoundaryRoute path={match.url} component={HorarioCursada} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={HorarioCursadaDeleteDialog} />
  </>
);

export default Routes;
