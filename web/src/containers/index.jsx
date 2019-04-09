import React from 'react';
import { Provider } from 'mobx-react';
import { Router, Switch, Route } from 'react-router';
import { createBrowserHistory } from 'history';
import * as stores from '../stores';
import { authHydrate } from '../utils/localPersist';
import Layout from '../layouts/Layout';
import Login from './login';
import routers from '../routers';

const history = createBrowserHistory();

authHydrate('Authorization', stores.authorization);

const Container = () => (
  <Provider {...stores}>
    <Router history={history}>
      <Switch>
        <Route exact path="/login" component={Login} />
        <Layout>
          <Switch>
            {routers.map(route => (
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
