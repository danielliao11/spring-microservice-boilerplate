import React from 'react';
import ReactPropTypes from 'prop-types';
import { PropTypes } from 'mobx-react';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';
import { Fab, Toolbar } from '@material-ui/core';
import { Add } from '@material-ui/icons';

// static
import TableToolBarStyle from '../../styles/jss/components/commons/tableToolBarStyle';

const TableToolBar = ({ ...props }) => {
  const { classes, store, searchBar } = props;
  return (
    <Toolbar className={classes.toolBar}>
      <Fab size="small" color="primary" aria-label="Add" className={classes.margin}>
        <Add />
      </Fab>
      {searchBar}
    </Toolbar>
  );
};

TableToolBar.defaultProps = {
  searchBar: <div />,
};

TableToolBar.propTypes = {
  classes: ReactPropTypes.shape().isRequired,
  searchBar: ReactPropTypes.element,
  store: PropTypes.observableObject.isRequired,
};

export default withStyles(TableToolBarStyle)(TableToolBar);
