import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProfesor } from 'app/shared/model/profesor.model';
import { getEntities as getProfesors } from 'app/entities/profesor/profesor.reducer';
import { IPeriodo } from 'app/shared/model/periodo.model';
import { getEntities as getPeriodos } from 'app/entities/periodo/periodo.reducer';
import { IMateria } from 'app/shared/model/materia.model';
import { getEntities as getMaterias } from 'app/entities/materia/materia.reducer';
import { getEntity, updateEntity, createEntity, reset } from './curso.reducer';
import { ICurso } from 'app/shared/model/curso.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface ICursoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export interface ICursoUpdateState {
  isNew: boolean;
  profesorId: number;
  periodoId: number;
  materiaId: number;
}

export class CursoUpdate extends React.Component<ICursoUpdateProps, ICursoUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      profesorId: 0,
      periodoId: 0,
      materiaId: 0,
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getProfesors();
    this.props.getPeriodos();
    this.props.getMaterias();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { cursoEntity } = this.props;
      const entity = {
        ...cursoEntity,
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
    this.props.history.push('/entity/curso');
  };

  profesorUpdate = element => {
    const id = element.target.value.toString();
    if (id === '') {
      this.setState({
        profesorId: -1
      });
    } else {
      for (const i in this.props.profesors) {
        if (id === this.props.profesors[i].id.toString()) {
          this.setState({
            profesorId: this.props.profesors[i].id
          });
        }
      }
    }
  };

  periodoUpdate = element => {
    const id = element.target.value.toString();
    if (id === '') {
      this.setState({
        periodoId: -1
      });
    } else {
      for (const i in this.props.periodos) {
        if (id === this.props.periodos[i].id.toString()) {
          this.setState({
            periodoId: this.props.periodos[i].id
          });
        }
      }
    }
  };

  materiaUpdate = element => {
    const id = element.target.value.toString();
    if (id === '') {
      this.setState({
        materiaId: -1
      });
    } else {
      for (const i in this.props.materias) {
        if (id === this.props.materias[i].id.toString()) {
          this.setState({
            materiaId: this.props.materias[i].id
          });
        }
      }
    }
  };

  render() {
    const { cursoEntity, profesors, periodos, materias, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="quechuaApp.curso.home.createOrEditLabel">
              {isNew ? 'Crear Curso' : 'Modificar Curso'}</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : cursoEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="curso-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="estadoLabel">Estado</Label>
                  <AvInput
                    id="curso-estado"
                    type="select"
                    className="form-control"
                    name="estado"
                    value={(!isNew && cursoEntity.estado) || 'ACTIVO'}
                  >
                    <option value="ACTIVO">ACTIVO</option>
                    <option value="INACTIVO">INACTIVO</option>
                    <option value="ELIMINADO">ELIMINADO</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="vacantesLabel" for="vacantes">
                    Vacantes
                  </Label>
                  <AvField
                    id="curso-vacantes"
                    type="number"
                    className="form-control"
                    name="vacantes"
                    validate={{
                      required: { value: true, errorMessage: 'Este campo es obligatorio.' },
                      number: { value: true, errorMessage: 'Este campo debe ser un número.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="numeroLabel" for="numero">
                    Número
                  </Label>
                  <AvField
                    id="curso-numero"
                    type="number"
                    className="form-control"
                    name="numero"
                    validate={{
                      required: { value: true, errorMessage: 'Este campo es obligatorio.' },
                      number: { value: true, errorMessage: 'Este campo debe ser un número.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="profesor.id">Profesor</Label>
                  <AvInput id="curso-profesor" type="select" className="form-control" name="profesor.id" onChange={this.profesorUpdate}>
                    {profesors
                      ? profesors.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nombre} {otherEntity.apellido}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="periodo.id">Periodo</Label>
                  <AvInput id="curso-periodo" type="select" className="form-control" name="periodo.id" onChange={this.periodoUpdate}>
                    {periodos
                      ? periodos.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.cuatrimestre}-{otherEntity.anio}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="materia.id">Materia</Label>
                  <AvInput id="curso-materia" type="select" className="form-control" name="materia.id" onChange={this.materiaUpdate}>
                    {materias
                      ? materias.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nombre} ({otherEntity.codigo})
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/curso" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />&nbsp;
                  <span className="d-none d-md-inline">Volver</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />&nbsp; Guardar
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
  profesors: storeState.profesor.entities,
  periodos: storeState.periodo.entities,
  materias: storeState.materia.entities,
  cursoEntity: storeState.curso.entity,
  loading: storeState.curso.loading,
  updating: storeState.curso.updating
});

const mapDispatchToProps = {
  getProfesors,
  getPeriodos,
  getMaterias,
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
)(CursoUpdate);
