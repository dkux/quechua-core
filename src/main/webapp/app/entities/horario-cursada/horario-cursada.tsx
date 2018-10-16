import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './horario-cursada.reducer';
import { IHorarioCursada } from 'app/shared/model/horario-cursada.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHorarioCursadaProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class HorarioCursada extends React.Component<IHorarioCursadaProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { horarioCursadaList, match } = this.props;
    return (
      <div>
        <h2 id="horario-cursada-heading">
          Horario Cursadas
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Horario Cursada
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Dia</th>
                <th>Sede</th>
                <th>Aula</th>
                <th>Hora Inicio</th>
                <th>Hora Fin</th>
                <th>Curso</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {horarioCursadaList.map((horarioCursada, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${horarioCursada.id}`} color="link" size="sm">
                      {horarioCursada.id}
                    </Button>
                  </td>
                  <td>{horarioCursada.dia}</td>
                  <td>{horarioCursada.sede}</td>
                  <td>{horarioCursada.aula}</td>
                  <td>{horarioCursada.horaInicio}</td>
                  <td>{horarioCursada.horaFin}</td>
                  <td>{horarioCursada.curso ? <Link to={`curso/${horarioCursada.curso.id}`}>
                    {horarioCursada.curso.materia.nombre} ({horarioCursada.curso.materia.codigo}) {horarioCursada.curso.profesor.nombre} {horarioCursada.curso.profesor.apellido}
                    </Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${horarioCursada.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${horarioCursada.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${horarioCursada.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ horarioCursada }: IRootState) => ({
  horarioCursadaList: horarioCursada.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(HorarioCursada);
