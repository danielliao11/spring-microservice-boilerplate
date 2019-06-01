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

@inject('authorization')
@observer
class Layout extends React.Component {
  render() {
    const {
      authorization, location, children, classes,
    } = this.props;
    // if (!authorization.authorized) {
    //   return <Redirect to="/login" />;
    // }
    return (
      <div className={classes.layout}>
        <SideBar routes={routes} className={classes.nav} />
        <div className={classes.right}>
          <TopBar />
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
};

export default withStyles(layoutStyle)(Layout);
