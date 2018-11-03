import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './coloquio.reducer';
import { IColoquio } from 'app/shared/model/coloquio.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IColoquioDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export class ColoquioDetail extends React.Component<IColoquioDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { coloquioEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Coloquio [<b>{coloquioEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="aula">Aula</span>
            </dt>
            <dd>{coloquioEntity.aula}</dd>
            <dt>
              <span id="horaInicio">Hora Inicio</span>
            </dt>
            <dd>{coloquioEntity.horaInicio}</dd>
            <dt>
              <span id="horaFin">Hora Fin</span>
            </dt>
            <dd>{coloquioEntity.horaFin}</dd>
            <dt>
              <span id="sede">Sede</span>
            </dt>
            <dd>{coloquioEntity.sede}</dd>
            <dt>
              <span id="fecha">Fecha</span>
            </dt>
            <dd>
              <TextFormat value={coloquioEntity.fecha} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="libro">Libro</span>
            </dt>
            <dd>{coloquioEntity.libro}</dd>
            <dt>
              <span id="folio">Folio</span>
            </dt>
            <dd>{coloquioEntity.folio}</dd>
            <dt>
              <span id="estado">Estado</span>
            </dt>
            <dd>{coloquioEntity.estado}</dd>
            <dt>Curso</dt>
            <dd>{coloquioEntity.curso ? coloquioEntity.curso.id : ''}</dd>
            <dt>Periodo</dt>
            <dd>{coloquioEntity.periodo ? coloquioEntity.periodo.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/coloquio" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/coloquio/${coloquioEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ coloquio }: IRootState) => ({
  coloquioEntity: coloquio.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ColoquioDetail);
