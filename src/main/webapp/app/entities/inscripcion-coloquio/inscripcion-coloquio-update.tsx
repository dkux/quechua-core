import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IColoquio } from 'app/shared/model/coloquio.model';
import { getEntities as getColoquios } from 'app/entities/coloquio/coloquio.reducer';
import { IAlumno } from 'app/shared/model/alumno.model';
import { getEntities as getAlumnos } from 'app/entities/alumno/alumno.reducer';
import { ICursada } from 'app/shared/model/cursada.model';
import { getEntities as getCursadas } from 'app/entities/cursada/cursada.reducer';
import { getEntity, updateEntity, createEntity, reset } from './inscripcion-coloquio.reducer';
import { IInscripcionColoquio } from 'app/shared/model/inscripcion-coloquio.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface IInscripcionColoquioUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export interface IInscripcionColoquioUpdateState {
  isNew: boolean;
  coloquioId: number;
  alumnoId: number;
  cursadaId: number;
}

export class InscripcionColoquioUpdate extends React.Component<IInscripcionColoquioUpdateProps, IInscripcionColoquioUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      coloquioId: 0,
      alumnoId: 0,
      cursadaId: 0,
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getColoquios();
    this.props.getAlumnos();
    this.props.getCursadas();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { inscripcionColoquioEntity } = this.props;
      const entity = {
        ...inscripcionColoquioEntity,
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
    this.props.history.push('/entity/inscripcion-coloquio');
  };

  coloquioUpdate = element => {
    const id = element.target.value.toString();
    if (id === '') {
      this.setState({
        coloquioId: -1
      });
    } else {
      for (const i in this.props.coloquios) {
        if (id === this.props.coloquios[i].id.toString()) {
          this.setState({
            coloquioId: this.props.coloquios[i].id
          });
        }
      }
    }
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

  cursadaUpdate = element => {
    const id = element.target.value.toString();
    if (id === '') {
      this.setState({
        cursadaId: -1
      });
    } else {
      for (const i in this.props.cursadas) {
        if (id === this.props.cursadas[i].id.toString()) {
          this.setState({
            cursadaId: this.props.cursadas[i].id
          });
        }
      }
    }
  };

  render() {
    const { inscripcionColoquioEntity, coloquios, alumnos, cursadas, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="quechuaApp.inscripcionColoquio.home.createOrEditLabel">Create or edit a InscripcionColoquio</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : inscripcionColoquioEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="inscripcion-coloquio-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="estadoLabel">Estado</Label>
                  <AvInput
                    id="inscripcion-coloquio-estado"
                    type="select"
                    className="form-control"
                    name="estado"
                    value={(!isNew && inscripcionColoquioEntity.estado) || 'ACTIVA'}
                  >
                    <option value="ACTIVA">ACTIVA</option>
                    <option value="ELIMINADA">ELIMINADA</option>
                    <option value="APROBADA">APROBADA</option>
                    <option value="DESAPROBADA">DESAPROBADA</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="coloquio.id">Coloquio</Label>
                  <AvInput
                    id="inscripcion-coloquio-coloquio"
                    type="select"
                    className="form-control"
                    name="coloquio.id"
                    onChange={this.coloquioUpdate}
                  >
                    <option value="" key="0" />
                    {coloquios
                      ? coloquios.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="alumno.id">Alumno</Label>
                  <AvInput
                    id="inscripcion-coloquio-alumno"
                    type="select"
                    className="form-control"
                    name="alumno.id"
                    onChange={this.alumnoUpdate}
                  >
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
                  <Label for="cursada.id">Cursada</Label>
                  <AvInput
                    id="inscripcion-coloquio-cursada"
                    type="select"
                    className="form-control"
                    name="cursada.id"
                    onChange={this.cursadaUpdate}
                    value={isNew && cursadas ? cursadas[0] && cursadas[0].id : ''}
                  >
                    {cursadas
                      ? cursadas.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/inscripcion-coloquio" replace color="info">
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
  coloquios: storeState.coloquio.entities,
  alumnos: storeState.alumno.entities,
  cursadas: storeState.cursada.entities,
  inscripcionColoquioEntity: storeState.inscripcionColoquio.entity,
  loading: storeState.inscripcionColoquio.loading,
  updating: storeState.inscripcionColoquio.updating
});

const mapDispatchToProps = {
  getColoquios,
  getAlumnos,
  getCursadas,
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
)(InscripcionColoquioUpdate);
