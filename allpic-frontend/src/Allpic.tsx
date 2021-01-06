import React from 'react';

import 'rbx/index.css';
import {Content} from "rbx";
import {BrowserRouter as Router, Switch, Route} from "react-router-dom";
import MenuBar from "./components/MenuBar";
import {AuthenticationContextProvider} from "./contexts/AuthenticationContext";
import {APIMethods} from "./services/ApiActions";
import Upload from "./views/Upload";
import useModal from "./hooks/useModal";
import LoginModal from "./components/LoginModal";
import RegisterModal from "./components/RegisterModal";

const Allpic = () => {

  const loginModal = useModal();
  const registerModal = useModal();

  return (
    <AuthenticationContextProvider value={APIMethods.getAuth()}>
      <Router>
        <Content>
          <MenuBar loginModal={loginModal} registerModal={registerModal}/>
          <Switch>
            {loginModal.isShowing ? <LoginModal modalHook={loginModal} /> : ''}
            {registerModal.isShowing ? <RegisterModal modalHook={registerModal} /> : ''}
            <Route path="/upload">
              <Upload />
            </Route>
            <Route path="/">
              <h1>hello world</h1>
            </Route>
          </Switch>
        </Content>
      </Router>
    </AuthenticationContextProvider>
  );
};

export default Allpic;
