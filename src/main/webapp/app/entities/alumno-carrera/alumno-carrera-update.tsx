import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAlumno } from 'app/shared/model/alumno.model';
import { getEntities as getAlumnos } from 'app/entities/alumno/alumno.reducer';
import { ICarrera } from 'app/shared/model/carrera.model';
import { getEntities as getCarreras } from 'app/entities/carrera/carrera.reducer';
import { getEntity, updateEntity, createEntity, reset } from './alumno-carrera.reducer';
import { IAlumnoCarrera } from 'app/shared/model/alumno-carrera.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface IAlumnoCarreraUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export interface IAlumnoCarreraUpdateState {
  isNew: boolean;
  alumnoId: number;
  carreraId: number;
}

export class AlumnoCarreraUpdate extends React.Component<IAlumnoCarreraUpdateProps, IAlumnoCarreraUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      alumnoId: 0,
      carreraId: 0,
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getAlumnos();
    this.props.getCarreras();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { alumnoCarreraEntity } = this.props;
      const entity = {
        ...alumnoCarreraEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/alumno-carrera');
  };

  alumnoUpdate = element => {
    const id = element.target.value.toString();
    if (id === '') {
      this.setState({
        alumnoId: -1
      });
    } else {
      for (const i in this.props.alumnos) {
        if (id === this.props.alumnos[i].id.toString()) {
          this.setState({
            alumnoId: this.props.alumnos[i].id
          });
        }
      }
    }
  };

  carreraUpdate = element => {
    const id = element.target.value.toString();
    if (id === '') {
      this.setState({
        carreraId: -1
      });
    } else {
      for (const i in this.props.carreras) {
        if (id === this.props.carreras[i].id.toString()) {
          this.setState({
            carreraId: this.props.carreras[i].id
          });
        }
      }
    }
  };

  render() {
    const { alumnoCarreraEntity, alumnos, carreras, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="quechuaApp.alumnoCarrera.home.createOrEditLabel">Create or edit a AlumnoCarrera</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : alumnoCarreraEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="alumno-carrera-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label for="alumno.id">Alumno</Label>
                  <AvInput id="alumno-carrera-alumno" type="select" className="form-control" name="alumno.id" onChange={this.alumnoUpdate}>
                    <option value="" key="0" />
                    {alumnos
                      ? alumnos.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="carrera.id">Carrera</Label>
                  <AvInput
                    id="alumno-carrera-carrera"
                    type="select"
                    className="form-control"
                    name="carrera.id"
                    onChange={this.carreraUpdate}
                  >
                    <option value="" key="0" />
                    {carreras
                      ? carreras.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/alumno-carrera" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  alumnos: storeState.alumno.entities,
  carreras: storeState.carrera.entities,
  alumnoCarreraEntity: storeState.alumnoCarrera.entity,
  loading: storeState.alumnoCarrera.loading,
  updating: storeState.alumnoCarrera.updating
});

const mapDispatchToProps = {
  getAlumnos,
  getCarreras,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AlumnoCarreraUpdate);
