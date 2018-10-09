import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAlumno } from 'app/shared/model/alumno.model';
import { getEntities as getAlumnos } from 'app/entities/alumno/alumno.reducer';
import { ICurso } from 'app/shared/model/curso.model';
import { getEntities as getCursos } from 'app/entities/curso/curso.reducer';
import { getEntity, updateEntity, createEntity, reset } from './inscripcion-curso.reducer';
import { IInscripcionCurso } from 'app/shared/model/inscripcion-curso.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface IInscripcionCursoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IInscripcionCursoUpdateState {
  isNew: boolean;
  alumnoId: number;
  cursoId: number;
}

export class InscripcionCursoUpdate extends React.Component<IInscripcionCursoUpdateProps, IInscripcionCursoUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      alumnoId: 0,
      cursoId: 0,
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
    this.props.getCursos();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { inscripcionCursoEntity } = this.props;
      const entity = {
        ...inscripcionCursoEntity,
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
    this.props.history.push('/entity/inscripcion-curso');
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

  render() {
    const { inscripcionCursoEntity, alumnos, cursos, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="quechuaApp.inscripcionCurso.home.createOrEditLabel">Create or edit a InscripcionCurso</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : inscripcionCursoEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="inscripcion-curso-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="estadoLabel">Estado</Label>
                  <AvInput
                    id="inscripcion-curso-estado"
                    type="select"
                    className="form-control"
                    name="estado"
                    value={(!isNew && inscripcionCursoEntity.estado) || 'REGULAR'}
                  >
                    <option value="REGULAR">REGULAR</option>
                    <option value="CONDICIONAL">CONDICIONAL</option>
                    <option value="ELIMINADA">ELIMINADA</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="cursadaEstadoLabel">Cursada Estado</Label>
                  <AvInput
                    id="inscripcion-curso-cursadaEstado"
                    type="select"
                    className="form-control"
                    name="cursadaEstado"
                    value={(!isNew && inscripcionCursoEntity.cursadaEstado) || 'APROBADA'}
                  >
                    <option value="APROBADA">APROBADA</option>
                    <option value="DESAPROBADA">DESAPROBADA</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="alumno.id">Alumno</Label>
                  <AvInput
                    id="inscripcion-curso-alumno"
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
                  <Label for="curso.id">Curso</Label>
                  <AvInput id="inscripcion-curso-curso" type="select" className="form-control" name="curso.id" onChange={this.cursoUpdate}>
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
                <Button tag={Link} id="cancel-save" to="/entity/inscripcion-curso" replace color="info">
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
  cursos: storeState.curso.entities,
  inscripcionCursoEntity: storeState.inscripcionCurso.entity,
  loading: storeState.inscripcionCurso.loading,
  updating: storeState.inscripcionCurso.updating
});

const mapDispatchToProps = {
  getAlumnos,
  getCursos,
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
)(InscripcionCursoUpdate);
