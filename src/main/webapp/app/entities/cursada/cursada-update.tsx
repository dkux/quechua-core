import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ICurso } from 'app/shared/model/curso.model';
import { getEntities as getCursos } from 'app/entities/curso/curso.reducer';
import { IAlumno } from 'app/shared/model/alumno.model';
import { getEntities as getAlumnos } from 'app/entities/alumno/alumno.reducer';
import { getEntity, updateEntity, createEntity, reset } from './cursada.reducer';
import { ICursada } from 'app/shared/model/cursada.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface ICursadaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export interface ICursadaUpdateState {
  isNew: boolean;
  cursoId: number;
  alumnoId: number;
}

export class CursadaUpdate extends React.Component<ICursadaUpdateProps, ICursadaUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      cursoId: 0,
      alumnoId: 0,
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getCursos();
    this.props.getAlumnos();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { cursadaEntity } = this.props;
      const entity = {
        ...cursadaEntity,
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
    this.props.history.push('/entity/cursada');
  };

  cursoUpdate = element => {
    const id = element.target.value.toString();
    if (id === '') {
      this.setState({
        cursoId: -1
      });
    } else {
      for (const i in this.props.cursos) {
        if (id === this.props.cursos[i].id.toString()) {
          this.setState({
            cursoId: this.props.cursos[i].id
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

  render() {
    const { cursadaEntity, cursos, alumnos, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="quechuaApp.cursada.home.createOrEditLabel">Create or edit a Cursada</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : cursadaEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="cursada-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="notaCursadaLabel" for="notaCursada">
                    Nota Cursada
                  </Label>
                  <AvField id="cursada-notaCursada" type="number" className="form-control" name="notaCursada" />
                </AvGroup>
                <AvGroup>
                  <Label id="notFinalLabel" for="notFinal">
                    Not Final
                  </Label>
                  <AvField id="cursada-notFinal" type="number" className="form-control" name="notFinal" />
                </AvGroup>
                <AvGroup>
                  <Label id="libroLabel" for="libro">
                    Libro
                  </Label>
                  <AvField id="cursada-libro" type="text" name="libro" />
                </AvGroup>
                <AvGroup>
                  <Label id="folioLabel" for="folio">
                    Folio
                  </Label>
                  <AvField id="cursada-folio" type="text" name="folio" />
                </AvGroup>
                <AvGroup>
                  <Label id="estadoLabel">Estado</Label>
                  <AvInput
                    id="cursada-estado"
                    type="select"
                    className="form-control"
                    name="estado"
                    value={(!isNew && cursadaEntity.estado) || 'APROBADO'}
                  >
                    <option value="APROBADO">APROBADO</option>
                    <option value="REPROBADO">REPROBADO</option>
                    <option value="FINAL_PENDIENTE">FINAL_PENDIENTE</option>
                    <option value="FINAL_APROBADO">FINAL_APROBADO</option>
                    <option value="APLAZO">APLAZO</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="curso.id">Curso</Label>
                  <AvInput id="cursada-curso" type="select" className="form-control" name="curso.id" onChange={this.cursoUpdate}>
                    <option value="" key="0" />
                    {cursos
                      ? cursos.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="alumno.id">Alumno</Label>
                  <AvInput id="cursada-alumno" type="select" className="form-control" name="alumno.id" onChange={this.alumnoUpdate}>
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
                <Button tag={Link} id="cancel-save" to="/entity/cursada" replace color="info">
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
  cursos: storeState.curso.entities,
  alumnos: storeState.alumno.entities,
  cursadaEntity: storeState.cursada.entity,
  loading: storeState.cursada.loading,
  updating: storeState.cursada.updating
});

const mapDispatchToProps = {
  getCursos,
  getAlumnos,
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
)(CursadaUpdate);
