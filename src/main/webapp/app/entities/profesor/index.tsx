import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Profesor from './profesor';
import ProfesorDetail from './profesor-detail';
import ProfesorUpdate from './profesor-update';
import ProfesorDeleteDialog from './profesor-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProfesorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProfesorUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProfesorDetail} />
      <ErrorBoundaryRoute path={match.url} component={Profesor} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ProfesorDeleteDialog} />
  </>
);

export default Routes;
