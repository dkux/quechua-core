import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './alumno-carrera.reducer';
import { IAlumnoCarrera } from 'app/shared/model/alumno-carrera.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAlumnoCarreraProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class AlumnoCarrera extends React.Component<IAlumnoCarreraProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { alumnoCarreraList, match } = this.props;
    return (
      <div>
        <h2 id="alumno-carrera-heading">
          Alumno Carreras
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Alumno Carrera
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Alumno</th>
                <th>Carrera</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {alumnoCarreraList.map((alumnoCarrera, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${alumnoCarrera.id}`} color="link" size="sm">
                      {alumnoCarrera.id}
                    </Button>
                  </td>
                  <td>{alumnoCarrera.alumno ? <Link to={`alumno/${alumnoCarrera.alumno.id}`}>{alumnoCarrera.alumno.id}</Link> : ''}</td>
                  <td>{alumnoCarrera.carrera ? <Link to={`carrera/${alumnoCarrera.carrera.id}`}>{alumnoCarrera.carrera.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${alumnoCarrera.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${alumnoCarrera.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${alumnoCarrera.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ alumnoCarrera }: IRootState) => ({
  alumnoCarreraList: alumnoCarrera.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AlumnoCarrera);
