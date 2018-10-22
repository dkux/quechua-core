import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './horario-cursada.reducer';
import { IHorarioCursada } from 'app/shared/model/horario-cursada.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHorarioCursadaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export class HorarioCursadaDetail extends React.Component<IHorarioCursadaDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { horarioCursadaEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            HorarioCursada [<b>{horarioCursadaEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="dia">Dia</span>
            </dt>
            <dd>{horarioCursadaEntity.dia}</dd>
            <dt>
              <span id="sede">Sede</span>
            </dt>
            <dd>{horarioCursadaEntity.sede}</dd>
            <dt>
              <span id="aula">Aula</span>
            </dt>
            <dd>{horarioCursadaEntity.aula}</dd>
            <dt>
              <span id="horaInicio">Hora Inicio</span>
            </dt>
            <dd>{horarioCursadaEntity.horaInicio}</dd>
            <dt>
              <span id="horaFin">Hora Fin</span>
            </dt>
            <dd>{horarioCursadaEntity.horaFin}</dd>
            <dt>Curso</dt>
            <dd>{horarioCursadaEntity.curso ?
              `(${horarioCursadaEntity.curso.materia.codigo}) ${horarioCursadaEntity.curso.profesor.nombre} ${horarioCursadaEntity.curso.profesor.apellido}`
              : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/horario-cursada" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/horario-cursada/${horarioCursadaEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ horarioCursada }: IRootState) => ({
  horarioCursadaEntity: horarioCursada.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(HorarioCursadaDetail);
