import {Button, Navbar} from "rbx";
import {Link} from "react-router-dom";
import React, {useContext} from "react";
import {AuthenticationContext} from "../contexts/AuthenticationContext";
import {UseModalType} from "../hooks/useModal";

import './MenuBar.scss';

const MenuBar = ({ loginModal, registerModal }: MenuBarProps): JSX.Element => {

  const auth = useContext(AuthenticationContext).authenticated;

  const toggleLoginModal = (): void => {
    if (registerModal.isShowing) {
      registerModal.toggle();
    }

    loginModal.toggle();
  };

  const toggleRegisterModal = (): void => {
    if (loginModal.isShowing) {
      loginModal.toggle();
    }

    registerModal.toggle();
  };

  const loginRegisterOptions = () => {
    return (
      <>
        <Navbar.Item>
          <Button.Group>
            <Button color="primary" onClick={toggleRegisterModal}>
              <strong>Sign up</strong>
            </Button>
            <Button color="light" onClick={toggleLoginModal}>Log in</Button>
          </Button.Group>
        </Navbar.Item>
      </>
    );
  };

  const logoutOption = () => {
    return (
      <>
        <Link to="/logout">
          <Navbar.Item>Sign out</Navbar.Item>
        </Link>
      </>
    );
  };

  return (
    <Navbar>
      <Navbar.Brand>
        <Link to="/">
          <Navbar.Item>Home</Navbar.Item>
        </Link>
        <Link to="/upload">
          <Navbar.Item>Upload</Navbar.Item>
        </Link>
        <Navbar.Burger />
      </Navbar.Brand>
      <Navbar.Menu>
        <Navbar.Segment align="end">
          {auth ? logoutOption() : loginRegisterOptions()}
        </Navbar.Segment>
      </Navbar.Menu>
    </Navbar>
  );
};

export interface MenuBarProps {
  loginModal: UseModalType,
  registerModal: UseModalType
}

export default MenuBar;
