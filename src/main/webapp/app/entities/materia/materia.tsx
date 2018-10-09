import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './materia.reducer';
import { IMateria } from 'app/shared/model/materia.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMateriaProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Materia extends React.Component<IMateriaProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { materiaList, match } = this.props;
    return (
      <div>
        <h2 id="materia-heading">
          Materias
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />&nbsp; Create new Materia
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Codigo</th>
                <th>Creditos</th>
                <th>Departamento</th>
                <th>Carrera</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {materiaList.map((materia, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${materia.id}`} color="link" size="sm">
                      {materia.id}
                    </Button>
                  </td>
                  <td>{materia.nombre}</td>
                  <td>{materia.codigo}</td>
                  <td>{materia.creditos}</td>
                  <td>
                    {materia.departamento ? <Link to={`departamento/${materia.departamento.id}`}>{materia.departamento.id}</Link> : ''}
                  </td>
                  <td>{materia.carrera ? <Link to={`carrera/${materia.carrera.id}`}>{materia.carrera.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${materia.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${materia.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${materia.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ materia }: IRootState) => ({
  materiaList: materia.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Materia);
