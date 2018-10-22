import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IHorarioCursada } from 'app/shared/model/horario-cursada.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './horario-cursada.reducer';

export interface IHorarioCursadaDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export class HorarioCursadaDeleteDialog extends React.Component<IHorarioCursadaDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.horarioCursadaEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { horarioCursadaEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>Confirmación de eliminación</ModalHeader>
        <ModalBody>Está seguro de eliminar este Horario de Cursada?</ModalBody>
        <ModalFooter>
          <Button color="secondary" onClick={this.handleClose}>
            <FontAwesomeIcon icon="ban" />&nbsp; Cancelar
          </Button>
          <Button color="danger" onClick={this.confirmDelete}>
            <FontAwesomeIcon icon="trash" />&nbsp; Eliminar
          </Button>
        </ModalFooter>
      </Modal>
    );
  }
}

const mapStateToProps = ({ horarioCursada }: IRootState) => ({
  horarioCursadaEntity: horarioCursada.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(HorarioCursadaDeleteDialog);
