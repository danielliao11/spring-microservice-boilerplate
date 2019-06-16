import React from 'react';
import ReactPropTypes from 'prop-types';
import { PropTypes } from 'mobx-react';

// @material-ui/core components
import makeStyles from '@material-ui/core/styles/makeStyles';
import {
  FormControl, InputLabel, MenuItem, Select,
} from '@material-ui/core';

// core component
import BootstrapInput from '../commons/BootstrapInput';

const useStyles = makeStyles(theme => ({
  root: {
    display: 'flex',
    flexWrap: 'wrap',
  },
  margin: {
    margin: theme.spacing(1),
  },
}));

const Tool = ({ ...props }) => {
  const { array, store } = props;
  const classes = useStyles();
  return (
    <form className={classes.root} autoComplete="off">
      <FormControl className={classes.margin}>
        <InputLabel htmlFor="name-input">Name</InputLabel>
        <BootstrapInput id="name-input" value={store.queryParam.name} onChange={e => store.handParamSearch(e)} />
      </FormControl>
      <FormControl className={classes.margin}>
        <InputLabel htmlFor="status-select">Status</InputLabel>
        <Select
          value=""
          onChange={e => store.handParamSearch(e)}
          input={<BootstrapInput name="status" id="status-select" />}
        >
          <MenuItem value="">
            <em>None</em>
          </MenuItem>
          {array.map(item => (
            <MenuItem key={item.value} value={item.value}>{item.name}</MenuItem>
          ))}
        </Select>
      </FormControl>
    </form>
  );
};

Tool.propTypes = {
  array: ReactPropTypes.arrayOf(ReactPropTypes.shape()).isRequired,
  store: PropTypes.observableObject.isRequired,
};

export default Tool;
