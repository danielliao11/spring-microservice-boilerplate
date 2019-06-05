import React from 'react';
import ReactPropTypes from 'prop-types';
import { observer, inject, PropTypes } from 'mobx-react';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';

// core
import TableCard from '../components/commons/TableCard';

// static
import resourcePageStyle from '../styles/jss/containers/resourcePageStyle';

class ResourcePage extends React.Component {
  render() {
    const { classes } = this.props;
    return (
      <TableCard />
    );
  }
}

ResourcePage.propTypes = {
  classes: ReactPropTypes.shape().isRequired,
};

export default withStyles(resourcePageStyle)(ResourcePage);
