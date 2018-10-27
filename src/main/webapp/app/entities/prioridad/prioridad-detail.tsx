import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './prioridad.reducer';
import { IPrioridad } from 'app/shared/model/prioridad.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPrioridadDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export class PrioridadDetail extends React.Component<IPrioridadDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { prioridadEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Prioridad [<b>{prioridadEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="fecha_habilitacion">Fecha Habilitacion</span>
            </dt>
            <dd>
              <TextFormat value={prioridadEntity.fecha_habilitacion} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>Periodo</dt>
            <dd>{prioridadEntity.periodo ? prioridadEntity.periodo.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/prioridad" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/prioridad/${prioridadEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ prioridad }: IRootState) => ({
  prioridadEntity: prioridad.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PrioridadDetail);
