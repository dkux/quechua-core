import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './materia.reducer';
import { IMateria } from 'app/shared/model/materia.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMateriaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export class MateriaDetail extends React.Component<IMateriaDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { materiaEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Materia [<b>{materiaEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nombre">Nombre</span>
            </dt>
            <dd>{materiaEntity.nombre}</dd>
            <dt>
              <span id="codigo">Codigo</span>
            </dt>
            <dd>{materiaEntity.codigo}</dd>
            <dt>
              <span id="creditos">Creditos</span>
            </dt>
            <dd>{materiaEntity.creditos}</dd>
            <dt>Departamento</dt>
            <dd>{materiaEntity.departamento ? materiaEntity.departamento.id : ''}</dd>
            <dt>Carrera</dt>
            <dd>{materiaEntity.carrera ? materiaEntity.carrera.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/materia" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/materia/${materiaEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ materia }: IRootState) => ({
  materiaEntity: materia.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(MateriaDetail);
