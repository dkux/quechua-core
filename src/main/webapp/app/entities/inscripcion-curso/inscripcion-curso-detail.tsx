import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './inscripcion-curso.reducer';
import { IInscripcionCurso } from 'app/shared/model/inscripcion-curso.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IInscripcionCursoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export class InscripcionCursoDetail extends React.Component<IInscripcionCursoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { inscripcionCursoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            InscripcionCurso [<b>{inscripcionCursoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="estado">Estado</span>
            </dt>
            <dd>{inscripcionCursoEntity.estado}</dd>
            <dt>
              <span id="cursadaEstado">Cursada Estado</span>
            </dt>
            <dd>{inscripcionCursoEntity.cursadaEstado}</dd>
            <dt>Alumno</dt>
            <dd>{inscripcionCursoEntity.alumno ? inscripcionCursoEntity.alumno.id : ''}</dd>
            <dt>Curso</dt>
            <dd>{inscripcionCursoEntity.curso ? inscripcionCursoEntity.curso.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/inscripcion-curso" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/inscripcion-curso/${inscripcionCursoEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ inscripcionCurso }: IRootState) => ({
  inscripcionCursoEntity: inscripcionCurso.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(InscripcionCursoDetail);
