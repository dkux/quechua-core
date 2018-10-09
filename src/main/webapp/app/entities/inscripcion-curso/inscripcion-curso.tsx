import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './inscripcion-curso.reducer';
import { IInscripcionCurso } from 'app/shared/model/inscripcion-curso.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInscripcionCursoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class InscripcionCurso extends React.Component<IInscripcionCursoProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { inscripcionCursoList, match } = this.props;
    return (
      <div>
        <h2 id="inscripcion-curso-heading">
          Inscripcion Cursos
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Inscripcion Curso
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Estado</th>
                <th>Cursada Estado</th>
                <th>Alumno</th>
                <th>Curso</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {inscripcionCursoList.map((inscripcionCurso, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${inscripcionCurso.id}`} color="link" size="sm">
                      {inscripcionCurso.id}
                    </Button>
                  </td>
                  <td>{inscripcionCurso.estado}</td>
                  <td>{inscripcionCurso.cursadaEstado}</td>
                  <td>
                    {inscripcionCurso.alumno ? <Link to={`alumno/${inscripcionCurso.alumno.id}`}>{inscripcionCurso.alumno.id}</Link> : ''}
                  </td>
                  <td>
                    {inscripcionCurso.curso ? <Link to={`curso/${inscripcionCurso.curso.id}`}>{inscripcionCurso.curso.id}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${inscripcionCurso.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${inscripcionCurso.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${inscripcionCurso.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ inscripcionCurso }: IRootState) => ({
  inscripcionCursoList: inscripcionCurso.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(InscripcionCurso);
