import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './periodo-administrativo.reducer';
import { IPeriodoAdministrativo } from 'app/shared/model/periodo-administrativo.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPeriodoAdministrativoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export class PeriodoAdministrativoDetail extends React.Component<IPeriodoAdministrativoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { periodoAdministrativoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            PeriodoAdministrativo [<b>{periodoAdministrativoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="fechaInicio">Fecha Inicio</span>
            </dt>
            <dd>
              <TextFormat value={periodoAdministrativoEntity.fechaInicio} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="fechaFin">Fecha Fin</span>
            </dt>
            <dd>
              <TextFormat value={periodoAdministrativoEntity.fechaFin} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="actividad">Actividad</span>
            </dt>
            <dd>{periodoAdministrativoEntity.actividad}</dd>
            <dt>
              <span id="descripcion">Descripcion</span>
            </dt>
            <dd>{periodoAdministrativoEntity.descripcion}</dd>
          </dl>
          <Button tag={Link} to="/entity/periodo-administrativo" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/periodo-administrativo/${periodoAdministrativoEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ periodoAdministrativo }: IRootState) => ({
  periodoAdministrativoEntity: periodoAdministrativo.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PeriodoAdministrativoDetail);
