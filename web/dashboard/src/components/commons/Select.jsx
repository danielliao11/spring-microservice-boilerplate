import React from 'react';
import ReactPropTypes from 'prop-types';

// @material-ui/core components
import { Select, MenuItem } from '@material-ui/core';

const CustomSelect = ({ ...props }) => {
  const {
    array, handleChange, lable, selectId,
  } = props;
  return (
    <Select
      value={array}
      onChange={handleChange}
      inputProps={{
        name: lable,
        id: selectId,
      }}
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
  array: ReactPropTypes.arrayOf(ReactPropTypes.shape()).isRequired,
  handleChange: ReactPropTypes.func.isRequired,
  lable: ReactPropTypes.string.isRequired,
  selectId: ReactPropTypes.string.isRequired,
};

export default CustomSelect;
