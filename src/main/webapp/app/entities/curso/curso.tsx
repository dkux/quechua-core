import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './curso.reducer';
import { ICurso } from 'app/shared/model/curso.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICursoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Curso extends React.Component<ICursoProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { cursoList, match } = this.props;
    return (
      <div>
        <h2 id="curso-heading">
          Cursos
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Curso
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Estado</th>
                <th>Vacantes</th>
                <th>Profesor</th>
                <th>Periodo</th>
                <th>Materia</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {cursoList.map((curso, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${curso.id}`} color="link" size="sm">
                      {curso.id}
                    </Button>
                  </td>
                  <td>{curso.estado}</td>
                  <td>{curso.vacantes}</td>
                  <td>{curso.profesor ? <Link to={`profesor/${curso.profesor.id}`}>{curso.profesor.nombre}, {curso.profesor.apellido} </Link> : ''}</td>
                  <td>{curso.periodo ? <Link to={`periodo/${curso.periodo.id}`}>{curso.periodo.cuatrimestre}-{curso.periodo.anio}</Link> : ''}</td>
                  <td>{curso.materia ? <Link to={`materia/${curso.materia.id}`}>{curso.materia.nombre} ({curso.materia.codigo})</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${curso.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${curso.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${curso.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ curso }: IRootState) => ({
  cursoList: curso.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Curso);
