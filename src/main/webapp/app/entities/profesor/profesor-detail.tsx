import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './profesor.reducer';
import { IProfesor } from 'app/shared/model/profesor.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProfesorDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export class ProfesorDetail extends React.Component<IProfesorDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { profesorEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Profesor [<b>{profesorEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nombre">Nombre</span>
            </dt>
            <dd>{profesorEntity.nombre}</dd>
            <dt>
              <span id="apellido">Apellido</span>
            </dt>
            <dd>{profesorEntity.apellido}</dd>
          </dl>
          <Button tag={Link} to="/entity/profesor" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/profesor/${profesorEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ profesor }: IRootState) => ({
  profesorEntity: profesor.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProfesorDetail);
