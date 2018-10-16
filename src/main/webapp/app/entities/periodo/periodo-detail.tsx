import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './periodo.reducer';
import { IPeriodo } from 'app/shared/model/periodo.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPeriodoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export class PeriodoDetail extends React.Component<IPeriodoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { periodoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Periodo [<b>{periodoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="cuatrimestre">Cuatrimestre</span>
            </dt>
            <dd>{periodoEntity.cuatrimestre}</dd>
            <dt>
              <span id="ano">Año</span>
            </dt>
            <dd>{periodoEntity.anio}</dd>
          </dl>
          <Button tag={Link} to="/entity/periodo" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/periodo/${periodoEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ periodo }: IRootState) => ({
  periodoEntity: periodo.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PeriodoDetail);
