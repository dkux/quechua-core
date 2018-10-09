import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PeriodoAdministrativo from './periodo-administrativo';
import PeriodoAdministrativoDetail from './periodo-administrativo-detail';
import PeriodoAdministrativoUpdate from './periodo-administrativo-update';
import PeriodoAdministrativoDeleteDialog from './periodo-administrativo-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PeriodoAdministrativoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PeriodoAdministrativoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PeriodoAdministrativoDetail} />
      <ErrorBoundaryRoute path={match.url} component={PeriodoAdministrativo} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PeriodoAdministrativoDeleteDialog} />
  </>
);

export default Routes;
