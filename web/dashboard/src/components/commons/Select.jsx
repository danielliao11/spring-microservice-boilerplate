import React from 'react';
import ReactPropTypes from 'prop-types';

// @material-ui/core components
import { Select, MenuItem } from '@material-ui/core';

// core components
import BootstrapInput from './BootstrapInput';

const CustomSelect = ({ ...props }) => {
  const {
    value, array, handleChange, name, id,
  } = props;
  return (
    <Select
      value={value}
      onChange={handleChange}
      input={<BootstrapInput name={name} id={id} />}
    >
      <MenuItem value="">
        <em>None</em>
      </MenuItem>
      {array.map(element => (
        <MenuItem value={element.value}>{element.name}</MenuItem>
      ))}
    </Select>
  );
};

CustomSelect.propTypes = {
  value: ReactPropTypes.string.isRequired,
  array: ReactPropTypes.arrayOf(ReactPropTypes.shape()).isRequired,
  handleChange: ReactPropTypes.func.isRequired,
  name: ReactPropTypes.string.isRequired,
  id: ReactPropTypes.string.isRequired,
};

export default CustomSelect;
