import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './periodo-administrativo.reducer';
import { IPeriodoAdministrativo } from 'app/shared/model/periodo-administrativo.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { keysToValues } from 'app/shared/util/entity-utils';

export interface IPeriodoAdministrativoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export interface IPeriodoAdministrativoUpdateState {
  isNew: boolean;
}

export class PeriodoAdministrativoUpdate extends React.Component<IPeriodoAdministrativoUpdateProps, IPeriodoAdministrativoUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { periodoAdministrativoEntity } = this.props;
      const entity = {
        ...periodoAdministrativoEntity,
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
    this.props.history.push('/entity/periodo-administrativo');
  };

  render() {
    const { periodoAdministrativoEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="quechuaApp.periodoAdministrativo.home.createOrEditLabel">Create or edit a PeriodoAdministrativo</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : periodoAdministrativoEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="periodo-administrativo-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="fechaInicioLabel" for="fechaInicio">
                    Fecha Inicio
                  </Label>
                  <AvField
                    id="periodo-administrativo-fechaInicio"
                    type="date"
                    className="form-control"
                    name="fechaInicio"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fechaFinLabel" for="fechaFin">
                    Fecha Fin
                  </Label>
                  <AvField
                    id="periodo-administrativo-fechaFin"
                    type="date"
                    className="form-control"
                    name="fechaFin"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="consultarPioridadLabel" check>
                    <AvInput
                      id="periodo-administrativo-consultarPioridad"
                      type="checkbox"
                      className="form-control"
                      name="consultarPioridad"
                    />
                    Consultar Pioridad
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="inscribirCursadaLabel" check>
                    <AvInput
                      id="periodo-administrativo-inscribirCursada"
                      type="checkbox"
                      className="form-control"
                      name="inscribirCursada"
                    />
                    Inscribir Cursada
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="inscribirColoquioLabel" check>
                    <AvInput
                      id="periodo-administrativo-inscribirColoquio"
                      type="checkbox"
                      className="form-control"
                      name="inscribirColoquio"
                    />
                    Inscribir Coloquio
                  </Label>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/periodo-administrativo" replace color="info">
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
  periodoAdministrativoEntity: storeState.periodoAdministrativo.entity,
  loading: storeState.periodoAdministrativo.loading,
  updating: storeState.periodoAdministrativo.updating
});

const mapDispatchToProps = {
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
)(PeriodoAdministrativoUpdate);
