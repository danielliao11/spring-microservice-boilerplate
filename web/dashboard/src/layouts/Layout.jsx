import React from 'react';
import ReactPropTypes from 'prop-types';
import { observer, inject, PropTypes } from 'mobx-react';
import { Redirect } from 'react-router-dom';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';

// core components
import NavBar from '../components/layout/NavBar';

const styles = {
  '@global': {
    html: {
      width: '100%',
      height: '100%',
      display: 'flex',
    },
    body: {
      width: '100%',
      height: '100%',
      display: 'flex',
    },
    '#app': {
      width: '100%',
      height: '100%',
      display: 'flex',
    },
  },
  layout: {
    width: '100%',
    height: '100%',
    display: 'flex',
  },
};

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
        <NavBar />
      </div>
    );
  }
}

Layout.propTypes = {
  children: ReactPropTypes.element.isRequired,
  location: ReactPropTypes.shape({
    pathname: ReactPropTypes.string,
  }).isRequired,
};

Layout.wrappedComponent.propTypes = {
  authorization: PropTypes.observableObject.isRequired,
};

export default withStyles(styles)(Layout);
