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
import { getEntity, updateEntity, createEntity, reset } from './horario-cursada.reducer';
import { IHorarioCursada } from 'app/shared/model/horario-cursada.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface IHorarioCursadaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export interface IHorarioCursadaUpdateState {
  isNew: boolean;
  cursoId: number;
}

export class HorarioCursadaUpdate extends React.Component<IHorarioCursadaUpdateProps, IHorarioCursadaUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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

    this.props.getCursos();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { horarioCursadaEntity } = this.props;
      const entity = {
        ...horarioCursadaEntity,
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
    this.props.history.push('/entity/horario-cursada');
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
    const { horarioCursadaEntity, cursos, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="quechuaApp.horarioCursada.home.createOrEditLabel">Create or edit a HorarioCursada</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : horarioCursadaEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="horario-cursada-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="diaLabel">Dia</Label>
                  <AvInput
                    id="horario-cursada-dia"
                    type="select"
                    className="form-control"
                    name="dia"
                    value={(!isNew && horarioCursadaEntity.dia) || 'LUNES'}
                  >
                    <option value="LUNES">LUNES</option>
                    <option value="MARTES">MARTES</option>
                    <option value="MIERCOLES">MIERCOLES</option>
                    <option value="JUEVES">JUEVES</option>
                    <option value="VIERNES">VIERNES</option>
                    <option value="SABADO">SABADO</option>
                    <option value="DOMINGO">DOMINGO</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="sedeLabel">Sede</Label>
                  <AvInput
                    id="horario-cursada-sede"
                    type="select"
                    className="form-control"
                    name="sede"
                    value={(!isNew && horarioCursadaEntity.sede) || 'PC'}
                  >
                    <option value="PC">PC</option>
                    <option value="LH">LH</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="aulaLabel" for="aula">
                    Aula
                  </Label>
                  <AvField
                    id="horario-cursada-aula"
                    type="text"
                    name="aula"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="horaInicioLabel" for="horaInicio">
                    Hora Inicio
                  </Label>
                  <AvField
                    id="horario-cursada-horaInicio"
                    type="text"
                    name="horaInicio"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      pattern: { value: '^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$', errorMessage: 'Formato inválido HH:MM.'}
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="horaFinLabel" for="horaFin">
                    Hora Fin
                  </Label>
                  <AvField
                    id="horario-cursada-horaFin"
                    type="text"
                    name="horaFin"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      pattern: { value: '^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$', errorMessage: 'Formato inválido HH:MM.'}
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="curso.id">Curso</Label>
                  <AvInput id="horario-cursada-curso" type="select" className="form-control" name="curso.id" onChange={this.cursoUpdate}>
                    {cursos
                      ? cursos.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            ({otherEntity.materia.codigo}) {otherEntity.profesor.nombre} {otherEntity.profesor.apellido}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/horario-cursada" replace color="info">
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
  horarioCursadaEntity: storeState.horarioCursada.entity,
  loading: storeState.horarioCursada.loading,
  updating: storeState.horarioCursada.updating
});

const mapDispatchToProps = {
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
)(HorarioCursadaUpdate);
