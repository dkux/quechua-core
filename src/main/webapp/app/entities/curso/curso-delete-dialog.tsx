import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICurso } from 'app/shared/model/curso.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './curso.reducer';

export interface ICursoDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id }> {}

export class CursoDeleteDialog extends React.Component<ICursoDeleteDialogProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  confirmDelete = event => {
    this.props.deleteEntity(this.props.cursoEntity.id);
    this.handleClose(event);
  };

  handleClose = event => {
    event.stopPropagation();
    this.props.history.goBack();
  };

  render() {
    const { cursoEntity } = this.props;
    return (
      <Modal isOpen toggle={this.handleClose}>
        <ModalHeader toggle={this.handleClose}>Confirmación de eliminación</ModalHeader>
        <ModalBody>Está seguro de querer eliminar este Curso?</ModalBody>
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

const mapStateToProps = ({ curso }: IRootState) => ({
  cursoEntity: curso.entity
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CursoDeleteDialog);
