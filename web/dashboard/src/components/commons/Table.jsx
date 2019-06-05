import React from 'react';
import ReactPropTypes from 'prop-types';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';
import Table from '@material-ui/core/Table';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';

// static
import tableStyle from '../../styles/jss/components/commons/tableStyle';

const CustomTable = ({ ...props }) => {
  const {
    classes,
    tableHead,
    tableBody,
    tableHeaderColor,
    tablePagination,
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
        {tableBody}
        {tablePagination}
      </Table>
    </div>
  );
};

CustomTable.defaultProps = {
  tableHeaderColor: 'gray',
  tableHead: [],
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
  tableHead: ReactPropTypes.arrayOf(ReactPropTypes.string),
  tableBody: ReactPropTypes.element,
  tablePagination: ReactPropTypes.element,
};

export default withStyles(tableStyle)(CustomTable);
