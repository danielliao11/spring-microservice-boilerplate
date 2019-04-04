import React from 'react';
import { Provider } from 'mobx-react';
import { Router, Switch, Route } from 'react-router';
import { createBrowserHistory, createHashHistory } from 'history';
import { supportsHistory } from 'history/DOMUtils';
import * as stores from '../stores';
import Layout from '../components/common/Layout';
import routes from '../routes';

const history = supportsHistory() ? createBrowserHistory() : createHashHistory();

const Container = () => (
  <Provider {...stores}>
    <Router history={history}>
      <Switch>
        {/* <Route exact path="/login" component={Login} /> */}
        <Layout>
          <Switch>
            {routes.map(route => (
              <Route
                location={history.location}
                exact
                key={route.path}
                {...route}
              />
            ))}
          </Switch>
        </Layout>
      </Switch>
    </Router>
  </Provider>
);

export default Container;
