import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './departamento.reducer';
import { IDepartamento } from 'app/shared/model/departamento.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDepartamentoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class DepartamentoDetail extends React.Component<IDepartamentoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { departamentoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Departamento [<b>{departamentoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nombre">Nombre</span>
            </dt>
            <dd>{departamentoEntity.nombre}</dd>
            <dt>
              <span id="codigo">Codigo</span>
            </dt>
            <dd>{departamentoEntity.codigo}</dd>
          </dl>
          <Button tag={Link} to="/entity/departamento" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/departamento/${departamentoEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ departamento }: IRootState) => ({
  departamentoEntity: departamento.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DepartamentoDetail);
