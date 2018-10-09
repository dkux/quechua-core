import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Coloquio from './coloquio';
import ColoquioDetail from './coloquio-detail';
import ColoquioUpdate from './coloquio-update';
import ColoquioDeleteDialog from './coloquio-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ColoquioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ColoquioUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ColoquioDetail} />
      <ErrorBoundaryRoute path={match.url} component={Coloquio} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ColoquioDeleteDialog} />
  </>
);

export default Routes;
