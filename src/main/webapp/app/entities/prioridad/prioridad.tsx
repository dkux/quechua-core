import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './prioridad.reducer';
import { IPrioridad } from 'app/shared/model/prioridad.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPrioridadProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Prioridad extends React.Component<IPrioridadProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { prioridadList, match } = this.props;
    return (
      <div>
        <h2 id="prioridad-heading">
          Prioridads
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Prioridad
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Fecha Habilitacion</th>
                <th>Periodo</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {prioridadList.map((prioridad, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${prioridad.id}`} color="link" size="sm">
                      {prioridad.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={prioridad.fecha_habilitacion} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{prioridad.periodo ? <Link to={`periodo/${prioridad.periodo.id}`}>{prioridad.periodo.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${prioridad.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${prioridad.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${prioridad.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ prioridad }: IRootState) => ({
  prioridadList: prioridad.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Prioridad);
