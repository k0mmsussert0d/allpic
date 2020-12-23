import React from 'react';

import 'rbx/index.css';
import {Content} from "rbx";
import {BrowserRouter as Router, Switch, Route} from "react-router-dom";
import MenuBar from "./components/MenuBar";
import {AuthenticationContextProvider} from "./contexts/AuthenticationContext";
import {APIMethods} from "./services/ApiActions";
import Upload from "./views/Upload";


const Allpic = () => {
  return (
    <AuthenticationContextProvider value={APIMethods.getAuth()}>
      <Router>
        <Content>
          <MenuBar/>
          <Switch>
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
