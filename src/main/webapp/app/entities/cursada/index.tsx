import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Cursada from './cursada';
import CursadaDetail from './cursada-detail';
import CursadaUpdate from './cursada-update';
import CursadaDeleteDialog from './cursada-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CursadaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CursadaUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CursadaDetail} />
      <ErrorBoundaryRoute path={match.url} component={Cursada} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={CursadaDeleteDialog} />
  </>
);

export default Routes;
