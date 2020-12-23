import {Navbar} from "rbx";
import {Link} from "react-router-dom";
import React, {useContext, useState} from "react";
import {AuthenticationContext} from "../contexts/AuthenticationContext";
import {UseModalType} from "../hooks/useModal";

const MenuBar = ({ loginModal, registerModal }: MenuBarProps): JSX.Element => {

  const auth = useContext(AuthenticationContext).authenticated;

  const loginRegisterOptions = () => {
    return (
      <>
        <Navbar.Item onClick={() => loginModal.toggle()}>Sign in</Navbar.Item>
        <Link to="/register">
          <Navbar.Item>Sign up</Navbar.Item>
        </Link>
      </>
    );
  }

  const logoutOption = () => {
    return (
      <>
        <Link to="/logout">
          <Navbar.Item>Sign out</Navbar.Item>
        </Link>
      </>
    );
  }

  return (
    <Navbar>
      <Navbar.Menu>
        <Navbar.Segment align="start">
          <Link to="/">
            <Navbar.Item>Home</Navbar.Item>
          </Link>
          <Link to="/upload">
            <Navbar.Item>Upload</Navbar.Item>
          </Link>
        </Navbar.Segment>
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
