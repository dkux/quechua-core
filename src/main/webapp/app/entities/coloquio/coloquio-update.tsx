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
import { IPeriodo } from 'app/shared/model/periodo.model';
import { getEntities as getPeriodos } from 'app/entities/periodo/periodo.reducer';
import { getEntity, updateEntity, createEntity, reset } from './coloquio.reducer';
import { IColoquio } from 'app/shared/model/coloquio.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface IColoquioUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export interface IColoquioUpdateState {
  isNew: boolean;
  cursoId: number;
  periodoId: number;
}

export class ColoquioUpdate extends React.Component<IColoquioUpdateProps, IColoquioUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      cursoId: 0,
      periodoId: 0,
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
    this.props.getPeriodos();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { coloquioEntity } = this.props;
      const entity = {
        ...coloquioEntity,
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
    this.props.history.push('/entity/coloquio');
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

  render() {
    const { coloquioEntity, cursos, periodos, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="quechuaApp.coloquio.home.createOrEditLabel">Create or edit a Coloquio</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : coloquioEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="coloquio-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="aulaLabel" for="aula">
                    Aula
                  </Label>
                  <AvField
                    id="coloquio-aula"
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
                    id="coloquio-horaInicio"
                    type="text"
                    name="horaInicio"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="horaFinLabel" for="horaFin">
                    Hora Fin
                  </Label>
                  <AvField
                    id="coloquio-horaFin"
                    type="text"
                    name="horaFin"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="sedeLabel">Sede</Label>
                  <AvInput
                    id="coloquio-sede"
                    type="select"
                    className="form-control"
                    name="sede"
                    value={(!isNew && coloquioEntity.sede) || 'PC'}
                  >
                    <option value="PC">PC</option>
                    <option value="LH">LH</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="fechaLabel" for="fecha">
                    Fecha
                  </Label>
                  <AvField
                    id="coloquio-fecha"
                    type="date"
                    className="form-control"
                    name="fecha"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="libroLabel" for="libro">
                    Libro
                  </Label>
                  <AvField id="coloquio-libro" type="text" name="libro" />
                </AvGroup>
                <AvGroup>
                  <Label id="folioLabel" for="folio">
                    Folio
                  </Label>
                  <AvField id="coloquio-folio" type="text" name="folio" />
                </AvGroup>
                <AvGroup>
                  <Label for="curso.id">Curso</Label>
                  <AvInput id="coloquio-curso" type="select" className="form-control" name="curso.id" onChange={this.cursoUpdate}>
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
                  <Label for="periodo.id">Periodo</Label>
                  <AvInput
                    id="coloquio-periodo"
                    type="select"
                    className="form-control"
                    name="periodo.id"
                    onChange={this.periodoUpdate}
                    value={isNew && periodos ? periodos[0] && periodos[0].id : ''}
                  >
                    {periodos
                      ? periodos.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/coloquio" replace color="info">
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
  periodos: storeState.periodo.entities,
  coloquioEntity: storeState.coloquio.entity,
  loading: storeState.coloquio.loading,
  updating: storeState.coloquio.updating
});

const mapDispatchToProps = {
  getCursos,
  getPeriodos,
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
)(ColoquioUpdate);
