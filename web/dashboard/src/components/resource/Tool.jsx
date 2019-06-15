import React from 'react';
import ReactPropTypes from 'prop-types';

// @material-ui/core components
import makeStyles from '@material-ui/core/styles/makeStyles';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';

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
  const { array } = props;
  const classes = useStyles();
  return (
    <form className={classes.root} autoComplete="off">
      <FormControl className={classes.margin}>
        <InputLabel htmlFor="name-input">Name</InputLabel>
        <BootstrapInput id="name-input" />
      </FormControl>
      <FormControl className={classes.margin}>
        <InputLabel htmlFor="status-select">Status</InputLabel>
        <Select
          value=""
          onChange={() => {}}
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
};

export default Tool;
