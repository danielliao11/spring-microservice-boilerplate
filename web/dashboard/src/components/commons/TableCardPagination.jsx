import React from 'react';
import { PropTypes } from 'mobx-react';

// @material-ui/core components
import { TableFooter, TableRow, TablePagination } from '@material-ui/core';

const TableCardPagination = ({ ...props }) => {
  const { tableStore } = props;
  return (
    <TableFooter>
      <TableRow>
        <TablePagination
          rowsPerPageOptions={[10, 20, 50]}
          component="div"
          count={tableStore.page.total}
          rowsPerPage={tableStore.queryParam.pageSize}
          page={tableStore.page.pages - 1}
          backIconButtonProps={{
            'aria-label': 'Previous Page',
          }}
          nextIconButtonProps={{
            'aria-label': 'Next Page',
          }}
          onChangePage={(e, page) => tableStore.handleChangePage(e, page)}
          onChangeRowsPerPage={e => tableStore.handleChangeRowsPerPage(e)}
        />
      </TableRow>
    </TableFooter>
  );
};

TableCardPagination.propTypes = {
  tableStore: PropTypes.observableObject.isRequired,
};

export default TableCardPagination;
