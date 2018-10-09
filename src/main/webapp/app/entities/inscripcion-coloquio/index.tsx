import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import InscripcionColoquio from './inscripcion-coloquio';
import InscripcionColoquioDetail from './inscripcion-coloquio-detail';
import InscripcionColoquioUpdate from './inscripcion-coloquio-update';
import InscripcionColoquioDeleteDialog from './inscripcion-coloquio-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InscripcionColoquioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InscripcionColoquioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InscripcionColoquioDetail} />
      <ErrorBoundaryRoute path={match.url} component={InscripcionColoquio} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={InscripcionColoquioDeleteDialog} />
  </>
);

export default Routes;
