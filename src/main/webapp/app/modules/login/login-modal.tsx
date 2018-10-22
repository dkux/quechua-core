import React from 'react';

import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Label, Alert, Row, Col } from 'reactstrap';
import { AvForm, AvField, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Link } from 'react-router-dom';

export interface ILoginModalProps {
  showModal: boolean;
  loginError: boolean;
  handleLogin: Function;
  handleClose: Function;
}

class LoginModal extends React.Component<ILoginModalProps> {
  handleSubmit = (event, errors, { username, password, rememberMe }) => {
    const { handleLogin } = this.props;
    handleLogin(username, password, rememberMe);
  };

  render() {
    const { loginError, handleClose } = this.props;

    return (
      <Modal isOpen={this.props.showModal} toggle={handleClose} backdrop="static" id="login-page" autoFocus={false}>
        <AvForm onSubmit={this.handleSubmit}>
          <ModalHeader id="login-title" toggle={handleClose}>
            Ingresar
          </ModalHeader>
          <ModalBody>
            <Row>
              <Col md="12">
                {loginError ? (
                  <Alert color="danger">
                    <strong>Falló el login!</strong> Por favor verifique sus datos y pruebe nuevamente.
                  </Alert>
                ) : null}
              </Col>
              <Col md="12">
                <AvField
                  name="username"
                  label="Nombre de usuario"
                  placeholder="Su nombre de usuario"
                  required
                  errorMessage="El nombre de usuario no puede estar vacío!"
                  autoFocus
                />
                <AvField
                  name="password"
                  type="password"
                  label="Contraseña"
                  placeholder="Contraseña"
                  required
                  errorMessage="La contraseña no puede estar vacía!"
                />
                <AvGroup check inline>
                  <Label className="form-check-label">
                    <AvInput type="checkbox" name="rememberMe" /> Recordarme
                  </Label>
                </AvGroup>
              </Col>
            </Row>
            <div className="mt-1">&nbsp;</div>
          </ModalBody>
          <ModalFooter>
            <Button color="secondary" onClick={handleClose} tabIndex="1">
              Cancelar
            </Button>{' '}
            <Button color="primary" type="submit">
              Iniciar sesion
            </Button>
          </ModalFooter>
        </AvForm>
      </Modal>
    );
  }
}

export default LoginModal;
