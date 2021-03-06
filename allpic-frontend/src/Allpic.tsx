import React, {useEffect, useState} from 'react';
import 'rbx/index.css';
import './Allpic.scss';
import {Content} from "rbx";
import {BrowserRouter as Router, Switch, Route} from "react-router-dom";
import MenuBar from "./components/MenuBar";
import {AuthenticationContextProvider, AuthenticationContextType} from "./contexts/AuthenticationContext";
import {APIMethods} from "./services/ApiActions";
import Upload from "./views/Upload";
import useModal from "./hooks/useModal";
import LoginModal from "./components/LoginModal";
import RegisterModal from "./components/RegisterModal";
import ImageView from "./views/ImageView";
import Home from './views/Home';
import Profile from './views/Profile';
import ChangePasswordModal from './components/ChangePasswordModal';

const unauthorizedContextValue: AuthenticationContextType = {
  authenticated: false,
  username: null
}

const Allpic = () => {

  const [authorizedUser, setAuthorizedUser] = useState<AuthenticationContextType>(unauthorizedContextValue);
  const loginModal = useModal();
  const registerModal = useModal();
  const changePasswordModal = useModal();

  useEffect(() => {
    APIMethods.getAuth()
      .then(value => {
        console.log('User authorized', value);
        setAuthorizedUser(value.response ?? unauthorizedContextValue)
      })
      .catch(reason => {
        console.log('Failed to authorize user session.', reason);
        setAuthorizedUser(unauthorizedContextValue);
      })
  }, []);

  return (
    <AuthenticationContextProvider value={authorizedUser}>
      <Router basename={process.env.PUBLIC_URL}>
        <Content>
          <MenuBar loginModal={loginModal} registerModal={registerModal}/>
          <Switch>
            {loginModal.isShowing && <LoginModal modalHook={loginModal} />}
            {registerModal.isShowing && <RegisterModal modalHook={registerModal} />}
            {changePasswordModal.isShowing && <ChangePasswordModal modalHook={changePasswordModal} />}
            <Route path="/upload">
              <Upload />
            </Route>
            <Route path="/profile">
              <Profile changePasswordModal={changePasswordModal} />
            </Route>
            <Route path="/:id" component={ImageView}/>
            <Route path="/">
              <Home />
            </Route>
          </Switch>
        </Content>
      </Router>
    </AuthenticationContextProvider>
  );
};

export default Allpic;
