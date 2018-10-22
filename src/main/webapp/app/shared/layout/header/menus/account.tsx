import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { NavLink as Link } from 'react-router-dom';

import { NavDropdown } from '../header-components';

const accountMenuItemsAuthenticated2 = (
  <>
    <DropdownItem tag={Link} to="/account/settings">
      <FontAwesomeIcon icon="wrench" /> Settings
    </DropdownItem>
    <DropdownItem tag={Link} to="/account/password">
      <FontAwesomeIcon icon="clock" /> Password
    </DropdownItem>

    <DropdownItem tag={Link} to="/logout">
      <FontAwesomeIcon icon="sign-out-alt" /> Salir
    </DropdownItem>
  </>
);

const accountMenuItemsAuthenticated = (
  <>

    <DropdownItem tag={Link} to="/logout">
      <FontAwesomeIcon icon="sign-out-alt" /> Salir
    </DropdownItem>
  </>
);

const accountMenuItems2 = (
  <>
    <DropdownItem id="login-item" tag={Link} to="/login">
      <FontAwesomeIcon icon="sign-in-alt" /> Ingresar
    </DropdownItem>
    <DropdownItem tag={Link} to="/register">
      <FontAwesomeIcon icon="sign-in-alt" /> Register
    </DropdownItem>
  </>
);

const accountMenuItems = (
  <>
    <DropdownItem id="login-item" tag={Link} to="/login">
      <FontAwesomeIcon icon="sign-in-alt" /> Ingresar
    </DropdownItem>
  </>
);

export const AccountMenu = ({ isAuthenticated = false }) => (
  <NavDropdown icon="user" name="Cuenta" id="account-menu">
    {isAuthenticated ? accountMenuItemsAuthenticated : accountMenuItems}
  </NavDropdown>
);

export default AccountMenu;
