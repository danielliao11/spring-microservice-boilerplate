import React from 'react';
import ReactPropTypes from 'prop-types';
import { observer, inject, PropTypes } from 'mobx-react';
import { Redirect } from 'react-router-dom';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';

// core components
import SideBar from '../components/layout/SideBar';
import TopBar from '../components/layout/TopBar';

// static
import routes from '../routers';
import layoutStyle from '../styles/jss/components/layout/layoutStyle';

@inject('authorization', 'configuration', 'notification')
@observer
class Layout extends React.Component {
  render() {
    const {
      location, children, classes, authorization, configuration, notification,
    } = this.props;
    console.log(location);
    // if (!authorization.authorized) {
    //   return <Redirect to="/login" />;
    // }
    return (
      <div className={classes.layout}>
        <SideBar routes={routes} className={classes.nav} />
        <div className={classes.right}>
          <TopBar notification={notification} />
          {children}
        </div>
      </div>
    );
  }
}

Layout.propTypes = {
  children: ReactPropTypes.element.isRequired,
  location: ReactPropTypes.shape({
    pathname: ReactPropTypes.string,
  }).isRequired,
  classes: ReactPropTypes.shape().isRequired,
};

Layout.wrappedComponent.propTypes = {
  authorization: PropTypes.observableObject.isRequired,
  configuration: PropTypes.observableObject.isRequired,
  notification: PropTypes.observableObject.isRequired,
};

export default withStyles(layoutStyle)(Layout);
