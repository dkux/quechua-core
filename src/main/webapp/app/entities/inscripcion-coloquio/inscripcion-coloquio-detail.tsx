import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './inscripcion-coloquio.reducer';
import { IInscripcionColoquio } from 'app/shared/model/inscripcion-coloquio.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInscripcionColoquioDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: number }> {}

export class InscripcionColoquioDetail extends React.Component<IInscripcionColoquioDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { inscripcionColoquioEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            InscripcionColoquio [<b>{inscripcionColoquioEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="estado">Estado</span>
            </dt>
            <dd>{inscripcionColoquioEntity.estado}</dd>
            <dt>Coloquio</dt>
            <dd>{inscripcionColoquioEntity.coloquio ? inscripcionColoquioEntity.coloquio.id : ''}</dd>
            <dt>Alumno</dt>
            <dd>{inscripcionColoquioEntity.alumno ? inscripcionColoquioEntity.alumno.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/inscripcion-coloquio" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/inscripcion-coloquio/${inscripcionColoquioEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ inscripcionColoquio }: IRootState) => ({
  inscripcionColoquioEntity: inscripcionColoquio.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(InscripcionColoquioDetail);
