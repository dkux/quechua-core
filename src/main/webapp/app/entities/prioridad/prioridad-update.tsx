import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPeriodo } from 'app/shared/model/periodo.model';
import { getEntities as getPeriodos } from 'app/entities/periodo/periodo.reducer';
import { getEntity, updateEntity, createEntity, reset } from './prioridad.reducer';
import { IPrioridad } from 'app/shared/model/prioridad.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface IPrioridadUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export interface IPrioridadUpdateState {
  isNew: boolean;
  periodoId: number;
}

export class PrioridadUpdate extends React.Component<IPrioridadUpdateProps, IPrioridadUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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

    this.props.getPeriodos();
  }

  saveEntity = (event, errors, values) => {
    values.fecha_habilitacion = new Date(values.fecha_habilitacion);

    if (errors.length === 0) {
      const { prioridadEntity } = this.props;
      const entity = {
        ...prioridadEntity,
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
    this.props.history.push('/entity/prioridad');
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
    const { prioridadEntity, periodos, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="quechuaApp.prioridad.home.createOrEditLabel">Create or edit a Prioridad</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : prioridadEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="prioridad-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="fecha_habilitacionLabel" for="fecha_habilitacion">
                    Fecha Habilitacion
                  </Label>
                  <AvInput
                    id="prioridad-fecha_habilitacion"
                    type="datetime-local"
                    className="form-control"
                    name="fecha_habilitacion"
                    value={isNew ? null : convertDateTimeFromServer(this.props.prioridadEntity.fecha_habilitacion)}
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="periodo.id">Periodo</Label>
                  <AvInput
                    id="prioridad-periodo"
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
                <Button tag={Link} id="cancel-save" to="/entity/prioridad" replace color="info">
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
  periodos: storeState.periodo.entities,
  prioridadEntity: storeState.prioridad.entity,
  loading: storeState.prioridad.loading,
  updating: storeState.prioridad.updating
});

const mapDispatchToProps = {
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
)(PrioridadUpdate);
