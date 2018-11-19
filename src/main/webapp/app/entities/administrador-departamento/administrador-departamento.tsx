import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './administrador-departamento.reducer';
import { IAdministradorDepartamento } from 'app/shared/model/administrador-departamento.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAdministradorDepartamentoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class AdministradorDepartamento extends React.Component<IAdministradorDepartamentoProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { administradorDepartamentoList, match } = this.props;
    return (
      <div>
        <h2 id="administrador-departamento-heading">
          Administrador Departamentos
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Administrador Departamento
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>User Id</th>
                <th>Departamento Id</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {administradorDepartamentoList.map((administradorDepartamento, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${administradorDepartamento.id}`} color="link" size="sm">
                      {administradorDepartamento.id}
                    </Button>
                  </td>
                  <td>{administradorDepartamento.userId}</td>
                  <td>{administradorDepartamento.departamentoId}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${administradorDepartamento.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${administradorDepartamento.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${administradorDepartamento.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ administradorDepartamento }: IRootState) => ({
  administradorDepartamentoList: administradorDepartamento.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AdministradorDepartamento);
