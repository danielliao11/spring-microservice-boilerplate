import React from 'react';
import ReactPropTypes from 'prop-types';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';
import {
  Table, TableHead, TableRow, TableCell,
} from '@material-ui/core';

// static
import tableStyle from '../../styles/jss/components/commons/tableStyle';

const CustomTableHeader = ({ ...props }) => {
  const { classes, tableHeaderColor, tableHeaderData } = props;
  return (
    <TableHead className={classes[`${tableHeaderColor}TableHeader`]}>
      <TableRow>
        {tableHeaderData.map(head => (
          <TableCell
            className={`${classes.tableCell} ${classes.tableHeadCell}`}
            key={head}
          >
            {head}
          </TableCell>
        ))}
      </TableRow>
    </TableHead>
  );
};

const CustomTable = ({ ...props }) => {
  const {
    classes, tableHeaderData, tableBody, tableHeaderColor, tablePagination,
  } = props;
  return (
    <div className={classes.tableResponsive}>
      <Table className={classes.table}>
        {tableHeaderData !== undefined ? (
          <CustomTableHeader
            classes={classes}
            tableHeaderColor={tableHeaderColor}
            tableHeaderData={tableHeaderData}
          />
        ) : null}
        {tableBody}
      </Table>
      {tablePagination}
    </div>
  );
};

CustomTableHeader.defaultProps = {
  tableHeaderColor: 'gray',
  tableHeaderData: [],
};

CustomTableHeader.propTypes = {
  classes: ReactPropTypes.shape().isRequired,
  tableHeaderColor: ReactPropTypes.oneOf([
    'warning',
    'primary',
    'danger',
    'success',
    'info',
    'rose',
    'gray',
  ]),
  tableHeaderData: ReactPropTypes.arrayOf(ReactPropTypes.string),
};

CustomTable.defaultProps = {
  tableHeaderColor: 'gray',
  tableHeaderData: [],
  tableBody: (<div />),
  tablePagination: (<div />),
};

CustomTable.propTypes = {
  classes: ReactPropTypes.shape().isRequired,
  tableHeaderColor: ReactPropTypes.oneOf([
    'warning',
    'primary',
    'danger',
    'success',
    'info',
    'rose',
    'gray',
  ]),
  tableHeaderData: ReactPropTypes.arrayOf(ReactPropTypes.string),
  tableBody: ReactPropTypes.element,
  tablePagination: ReactPropTypes.element,
};

export default withStyles(tableStyle)(CustomTable);
