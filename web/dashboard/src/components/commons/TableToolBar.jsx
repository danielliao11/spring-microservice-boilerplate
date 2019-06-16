import React from 'react';
import ReactPropTypes from 'prop-types';
import { PropTypes } from 'mobx-react';

// @material-ui/core components
import makeStyles from '@material-ui/core/styles/makeStyles';
import { Fab, Toolbar } from '@material-ui/core';
import { Add } from '@material-ui/icons';

const useStyles = makeStyles(theme => ({
  toolBar: {
    padding: 0,
  },
  fab: {
    marginTop: theme.spacing(2.5),
  },
  searchBar: {
    marginLeft: theme.spacing(1.5),
  },
}));

const TableToolBar = ({ ...props }) => {
  const classes = useStyles();
  const { store, searchBar } = props;
  return (
    <Toolbar className={classes.toolBar}>
      <Fab size="small" color="primary" aria-label="Add" className={classes.fab}>
        <Add />
      </Fab>
      <div className={classes.searchBar}>
        {searchBar}
      </div>
    </Toolbar>
  );
};

TableToolBar.defaultProps = {
  searchBar: <div />,
};

TableToolBar.propTypes = {
  searchBar: ReactPropTypes.element,
  store: PropTypes.observableObject.isRequired,
};

export default TableToolBar;
