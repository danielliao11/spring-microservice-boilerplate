import React from 'react';
import ReactPropTypes from 'prop-types';
// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';
import Table from '@material-ui/core/Table';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import { Email, Lock } from '@material-ui/icons';
// core components
import tableStyle from '../../styles/jss/components/commons/tableStyle';

const email = <Email />;

function CustomTable({ ...props }) {
  const {
    classes,
    tableHead,
    tableData,
    tableHeaderColor,
  } = props;
  return (
    <div className={classes.tableResponsive}>
      <Table className={classes.table}>
        {tableHead !== undefined ? (
          <TableHead className={classes[`${tableHeaderColor}'TableHeader'`]}>
            <TableRow>
              {tableHead.map((head, headKey) => (
                <TableCell
                  className={`${classes.tableCell}' '${classes.tableHeadCell}`}
                  key={headKey}
                >
                  {head}
                </TableCell>
              ))}
            </TableRow>
          </TableHead>
        ) : null}
        <TableBody>
          {tableData.map((tableBody, bodyKey) => (
            <TableRow key={bodyKey}>
              {tableBody.map((rowData, rowKey) => {
                console.log(rowData);
                return (
                  <TableCell className={classes.tableCell} key={rowKey}>
                    {rowData}
                  </TableCell>
                );
              })}
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
}

CustomTable.defaultProps = {
  tableHeaderColor: 'gray',
  tableHead: 'undefined',
  tableData: [],
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
  tableHead: ReactPropTypes.arrayOf(ReactPropTypes.string),
  tableData: ReactPropTypes.arrayOf(ReactPropTypes.arrayOf(ReactPropTypes.string)),
};

export default withStyles(tableStyle)(CustomTable);
