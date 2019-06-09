import React from 'react';
import ReactPropTypes from 'prop-types';
import { observer, inject, PropTypes } from 'mobx-react';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';

// core
import TableCard from '../components/commons/TableCard';

// static
import resourcePageStyle from '../styles/jss/containers/resourcePageStyle';

@inject('resource')
@observer
class ResourcePage extends React.Component {
  componentDidMount() {
    const { resource } = this.props;
    setTimeout(() => resource.fetchPage(), 500);
  }

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

ResourcePage.wrappedComponent.propTypes = {
  resource: PropTypes.observableObject.isRequired,
};

export default withStyles(resourcePageStyle)(ResourcePage);
