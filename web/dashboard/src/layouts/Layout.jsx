import React from 'react';
import ReactPropTypes from 'prop-types';
import { observer, inject, PropTypes } from 'mobx-react';
import { Redirect } from 'react-router-dom';

// core components
import Navbar from '../components/layout/NavBar';

@inject('authorization')
@observer
class Layout extends React.Component {
  render() {
    const {
      authorization, location, children,
    } = this.props;
    console.log(authorization.authorized);
    // if (!authorization.authorized) {
    //   return <Redirect to="/login" />;
    // }
    return (
      <div>
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

export default Layout;
