import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './curso.reducer';
import { ICurso } from 'app/shared/model/curso.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICursoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export class CursoDetail extends React.Component<ICursoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { cursoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Curso [<b>{cursoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="estado">Estado</span>
            </dt>
            <dd>{cursoEntity.estado}</dd>
            <dt>
              <span id="vacantes">Vacantes</span>
            </dt>
            <dd>{cursoEntity.vacantes}</dd>
            <dt>
              <span id="numero">Numero</span>
            </dt>
            <dd>{cursoEntity.numero}</dd>
            <dt>Profesor</dt>
            <dd>{cursoEntity.profesor ? `${cursoEntity.profesor.nombre} ${cursoEntity.profesor.apellido}` : ''}</dd>
            <dt>Periodo</dt>
            <dd>{cursoEntity.periodo ? `${cursoEntity.periodo.cuatrimestre}-${cursoEntity.periodo.anio}` : ''}</dd>
            <dt>Materia</dt>
            <dd>{cursoEntity.materia ? `${cursoEntity.materia.nombre}` : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/curso" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Volver</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/curso/${cursoEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ curso }: IRootState) => ({
  cursoEntity: curso.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CursoDetail);
