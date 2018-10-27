import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './alumno.reducer';
import { IAlumno } from 'app/shared/model/alumno.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAlumnoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export class AlumnoDetail extends React.Component<IAlumnoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { alumnoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Alumno [<b>{alumnoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nombre">Nombre</span>
            </dt>
            <dd>{alumnoEntity.nombre}</dd>
            <dt>
              <span id="apellido">Apellido</span>
            </dt>
            <dd>{alumnoEntity.apellido}</dd>
            <dt>
              <span id="padron">Padron</span>
            </dt>
            <dd>{alumnoEntity.padron}</dd>
            <dt>
              <span id="prioridad">Prioridad</span>
            </dt>
            <dd>{alumnoEntity.prioridad}</dd>
          </dl>
          <Button tag={Link} to="/entity/alumno" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/alumno/${alumnoEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ alumno }: IRootState) => ({
  alumnoEntity: alumno.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AlumnoDetail);
