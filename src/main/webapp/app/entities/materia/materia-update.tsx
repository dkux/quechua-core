import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDepartamento } from 'app/shared/model/departamento.model';
import { getEntities as getDepartamentos } from 'app/entities/departamento/departamento.reducer';
import { ICarrera } from 'app/shared/model/carrera.model';
import { getEntities as getCarreras } from 'app/entities/carrera/carrera.reducer';
import { getEntity, updateEntity, createEntity, reset } from './materia.reducer';
import { IMateria } from 'app/shared/model/materia.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface IMateriaUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export interface IMateriaUpdateState {
  isNew: boolean;
  departamentoId: number;
  carreraId: number;
}

export class MateriaUpdate extends React.Component<IMateriaUpdateProps, IMateriaUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      departamentoId: 0,
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

    this.props.getDepartamentos();
    this.props.getCarreras();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { materiaEntity } = this.props;
      const entity = {
        ...materiaEntity,
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
    this.props.history.push('/entity/materia');
  };

  departamentoUpdate = element => {
    const id = element.target.value.toString();
    if (id === '') {
      this.setState({
        departamentoId: -1
      });
    } else {
      for (const i in this.props.departamentos) {
        if (id === this.props.departamentos[i].id.toString()) {
          this.setState({
            departamentoId: this.props.departamentos[i].id
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
    const { materiaEntity, departamentos, carreras, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="quechuaApp.materia.home.createOrEditLabel">Create or edit a Materia</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : materiaEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="materia-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nombreLabel" for="nombre">
                    Nombre
                  </Label>
                  <AvField
                    id="materia-nombre"
                    type="text"
                    name="nombre"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="codigoLabel" for="codigo">
                    Codigo
                  </Label>
                  <AvField
                    id="materia-codigo"
                    type="text"
                    name="codigo"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="creditosLabel" for="creditos">
                    Creditos
                  </Label>
                  <AvField
                    id="materia-creditos"
                    type="number"
                    className="form-control"
                    name="creditos"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="departamento.id">Departamento</Label>
                  <AvInput
                    id="materia-departamento"
                    type="select"
                    className="form-control"
                    name="departamento.id"
                    onChange={this.departamentoUpdate}
                  >
                    <option value="" key="0" />
                    {departamentos
                      ? departamentos.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="carrera.id">Carrera</Label>
                  <AvInput id="materia-carrera" type="select" className="form-control" name="carrera.id" onChange={this.carreraUpdate}>
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
                <Button tag={Link} id="cancel-save" to="/entity/materia" replace color="info">
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
  departamentos: storeState.departamento.entities,
  carreras: storeState.carrera.entities,
  materiaEntity: storeState.materia.entity,
  loading: storeState.materia.loading,
  updating: storeState.materia.updating
});

const mapDispatchToProps = {
  getDepartamentos,
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
)(MateriaUpdate);
