import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/carrera">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Carrera
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/departamento">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Departamento
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/materia">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Materia
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/horario-cursada">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Horario Cursada
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/curso">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Curso
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/profesor">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Profesor
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/alumno">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Alumno
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/alumno-carrera">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Alumno Carrera
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/inscripcion-curso">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Inscripcion Curso
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/coloquio">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Coloquio
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/periodo">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Periodo
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/inscripcion-coloquio">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Inscripcion Coloquio
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/periodo-administrativo">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Periodo Administrativo
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/cursada">
      <FontAwesomeIcon icon="asterisk" />&nbsp;Cursada
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
