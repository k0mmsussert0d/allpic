import {Button, Navbar} from "rbx";
import {Link} from "react-router-dom";
import React, {useContext} from "react";
import {AuthenticationContext} from "../contexts/AuthenticationContext";
import {UseModalType} from "../hooks/useModal";

import './MenuBar.scss';
import {APIMethods} from "../services/ApiActions";

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

  const logout = async () => {
    await APIMethods.logout();
    window.location.href = "/";
  }

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
          <Navbar.Item>
            <Button.Group>
              <Button color="light" onClick={logout}>Log out</Button>
            </Button.Group>
          </Navbar.Item>
      </>
    );
  };

  return (
    <Navbar>
      <Navbar.Brand>
        <Link to="/">
          <Navbar.Link arrowless>Home</Navbar.Link>
        </Link>
        <Link to="/upload">
          <Navbar.Link arrowless>Upload</Navbar.Link>
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
