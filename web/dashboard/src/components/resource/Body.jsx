import React from 'react';
import ReactPropTypes from 'prop-types';

// @material-ui/core components
import { TableBody, TableRow, TableCell } from '@material-ui/core';

const ResourceTableBody = ({ ...props }) => {
  const { content } = props;
  return (
    <TableBody>
      {content.map(row => (
        <TableRow key={row.id}>
          <TableCell>{row.name}</TableCell>
          <TableCell>{row.description}</TableCell>
          <TableCell>{row.status}</TableCell>
          <TableCell>{row.createdAt}</TableCell>
        </TableRow>
      ))}
    </TableBody>
  );
};

ResourceTableBody.defaultProps = {
  content: [],
};

ResourceTableBody.propTypes = {
  content: ReactPropTypes.arrayOf(ReactPropTypes.shape()),
};

export default ResourceTableBody;
