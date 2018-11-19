import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './administrador-departamento.reducer';
import { IAdministradorDepartamento } from 'app/shared/model/administrador-departamento.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAdministradorDepartamentoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class AdministradorDepartamentoDetail extends React.Component<IAdministradorDepartamentoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { administradorDepartamentoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            AdministradorDepartamento [<b>{administradorDepartamentoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="userId">User Id</span>
            </dt>
            <dd>{administradorDepartamentoEntity.userId}</dd>
            <dt>
              <span id="departamentoId">Departamento Id</span>
            </dt>
            <dd>{administradorDepartamentoEntity.departamentoId}</dd>
          </dl>
          <Button tag={Link} to="/entity/administrador-departamento" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/administrador-departamento/${administradorDepartamentoEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ administradorDepartamento }: IRootState) => ({
  administradorDepartamentoEntity: administradorDepartamento.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AdministradorDepartamentoDetail);
