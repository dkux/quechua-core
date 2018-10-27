import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './periodo-administrativo.reducer';
import { IPeriodoAdministrativo } from 'app/shared/model/periodo-administrativo.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPeriodoAdministrativoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class PeriodoAdministrativo extends React.Component<IPeriodoAdministrativoProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { periodoAdministrativoList, match } = this.props;
    return (
      <div>
        <h2 id="periodo-administrativo-heading">
          Periodo Administrativos
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Periodo Administrativo
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Fecha Inicio</th>
                <th>Fecha Fin</th>
                <th>Actividad</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {periodoAdministrativoList.map((periodoAdministrativo, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${periodoAdministrativo.id}`} color="link" size="sm">
                      {periodoAdministrativo.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={periodoAdministrativo.fechaInicio} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={periodoAdministrativo.fechaFin} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{periodoAdministrativo.actividad}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${periodoAdministrativo.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${periodoAdministrativo.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${periodoAdministrativo.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ periodoAdministrativo }: IRootState) => ({
  periodoAdministrativoList: periodoAdministrativo.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PeriodoAdministrativo);
