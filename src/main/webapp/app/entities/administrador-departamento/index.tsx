import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AdministradorDepartamento from './administrador-departamento';
import AdministradorDepartamentoDetail from './administrador-departamento-detail';
import AdministradorDepartamentoUpdate from './administrador-departamento-update';
import AdministradorDepartamentoDeleteDialog from './administrador-departamento-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AdministradorDepartamentoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AdministradorDepartamentoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AdministradorDepartamentoDetail} />
      <ErrorBoundaryRoute path={match.url} component={AdministradorDepartamento} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={AdministradorDepartamentoDeleteDialog} />
  </>
);

export default Routes;
