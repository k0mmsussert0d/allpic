import React from 'react';

import 'rbx/index.css';
import {Content, Navbar} from "rbx";
import {BrowserRouter as Router, Link, Switch, Route} from "react-router-dom";


const Allpic = () => {
  return (
    <Router>
      <Content>
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
          </Navbar.Menu>
        </Navbar>
        <Switch>
          <Route path="/">
            <h1>hello world</h1>
          </Route>
        </Switch>
      </Content>
    </Router>
  );
};

export default Allpic;
