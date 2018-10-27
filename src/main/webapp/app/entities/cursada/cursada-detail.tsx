import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './cursada.reducer';
import { ICursada } from 'app/shared/model/cursada.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICursadaDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export class CursadaDetail extends React.Component<ICursadaDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { cursadaEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Cursada [<b>{cursadaEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="notaCursada">Nota Cursada</span>
            </dt>
            <dd>{cursadaEntity.notaCursada}</dd>
            <dt>
              <span id="notFinal">Not Final</span>
            </dt>
            <dd>{cursadaEntity.notFinal}</dd>
            <dt>
              <span id="libro">Libro</span>
            </dt>
            <dd>{cursadaEntity.libro}</dd>
            <dt>
              <span id="folio">Folio</span>
            </dt>
            <dd>{cursadaEntity.folio}</dd>
            <dt>
              <span id="estado">Estado</span>
            </dt>
            <dd>{cursadaEntity.estado}</dd>
            <dt>Curso</dt>
            <dd>{cursadaEntity.curso ? cursadaEntity.curso.id : ''}</dd>
            <dt>Alumno</dt>
            <dd>{cursadaEntity.alumno ? cursadaEntity.alumno.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/cursada" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>&nbsp;
          <Button tag={Link} to={`/entity/cursada/${cursadaEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ cursada }: IRootState) => ({
  cursadaEntity: cursada.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CursadaDetail);
