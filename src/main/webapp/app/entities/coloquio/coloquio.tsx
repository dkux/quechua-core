import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './coloquio.reducer';
import { IColoquio } from 'app/shared/model/coloquio.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IColoquioProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Coloquio extends React.Component<IColoquioProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { coloquioList, match } = this.props;
    return (
      <div>
        <h2 id="coloquio-heading">
          Coloquios
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Coloquio
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Aula</th>
                <th>Hora Inicio</th>
                <th>Hora Fin</th>
                <th>Sede</th>
                <th>Fecha</th>
                <th>Curso</th>
                <th>Periodo</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {coloquioList.map((coloquio, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${coloquio.id}`} color="link" size="sm">
                      {coloquio.id}
                    </Button>
                  </td>
                  <td>{coloquio.aula}</td>
                  <td>{coloquio.horaInicio}</td>
                  <td>{coloquio.horaFin}</td>
                  <td>{coloquio.sede}</td>
                  <td>
                    <TextFormat type="date" value={coloquio.fecha} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{coloquio.curso ? <Link to={`curso/${coloquio.curso.id}`}>{coloquio.curso.id}</Link> : ''}</td>
                  <td>{coloquio.periodo ? <Link to={`periodo/${coloquio.periodo.id}`}>{coloquio.periodo.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${coloquio.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${coloquio.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${coloquio.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ coloquio }: IRootState) => ({
  coloquioList: coloquio.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Coloquio);
