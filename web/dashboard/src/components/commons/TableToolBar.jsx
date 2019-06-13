import React from 'react';
import ReactPropTypes from 'prop-types';
import { PropTypes } from 'mobx-react';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';
import {
  Fab, IconButton, Toolbar, TextField,
} from '@material-ui/core';
import { Add } from '@material-ui/icons';

// custom
import Select from './Select';

// static
import TableToolBarStyle from '../../styles/jss/components/commons/TableToolBarStyle';
import ObjectStatus from '../../constants/ObjectStatus';

function handleChange(e) {
  console.log(e.target);
}

const TableToolBar = ({ ...props }) => {
  const { classes, store } = props;
  return (
    <Toolbar className={classes.toolBar}>
      <Fab size="small" color="primary" aria-label="Add" className={classes.margin}>
        <Add />
      </Fab>
      <div className={classes.searchGroup}>
        <TextField
          className={classes.searchItem}
          label="Name"
          value={store.queryParam.name}
          onChange={e => store.handleSearch('name', e)}
        />
        <Select
          className={classes.searchItem}
          array={ObjectStatus.array}
          onChange={e => handleChange(e)}
        />
      </div>
    </Toolbar>
  );
};

TableToolBar.propTypes = {
  classes: ReactPropTypes.shape().isRequired,
  store: PropTypes.observableObject.isRequired,
};

export default withStyles(TableToolBarStyle)(TableToolBar);
