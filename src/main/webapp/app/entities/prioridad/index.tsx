import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Prioridad from './prioridad';
import PrioridadDetail from './prioridad-detail';
import PrioridadUpdate from './prioridad-update';
import PrioridadDeleteDialog from './prioridad-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PrioridadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PrioridadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PrioridadDetail} />
      <ErrorBoundaryRoute path={match.url} component={Prioridad} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PrioridadDeleteDialog} />
  </>
);

export default Routes;
