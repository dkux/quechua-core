import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './alumno-carrera.reducer';
import { IAlumnoCarrera } from 'app/shared/model/alumno-carrera.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAlumnoCarreraDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class AlumnoCarreraDetail extends React.Component<IAlumnoCarreraDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { alumnoCarreraEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            AlumnoCarrera [<b>{alumnoCarreraEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>Alumno</dt>
            <dd>{alumnoCarreraEntity.alumno ? alumnoCarreraEntity.alumno.id : ''}</dd>
            <dt>Carrera</dt>
            <dd>{alumnoCarreraEntity.carrera ? alumnoCarreraEntity.carrera.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/alumno-carrera" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/alumno-carrera/${alumnoCarreraEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ alumnoCarrera }: IRootState) => ({
  alumnoCarreraEntity: alumnoCarrera.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AlumnoCarreraDetail);
