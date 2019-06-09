import React from 'react';
import ReactPropTypes from 'prop-types';
import { PropTypes } from 'mobx-react';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';
import { TableFooter, TableRow, TablePagination } from '@material-ui/core';

// core components
import Card from '../../material_kit/components/Card/Card';
import CardBody from '../../material_kit/components/Card/CardBody';
import CardHeader from '../../material_kit/components/Card/CardHeader';
import GridContainer from '../../material_kit/components/Grid/GridContainer';
import GridItem from '../../material_kit/components/Grid/GridItem';
import Table from './Table';

// static
import tableCardStyle from '../../styles/jss/components/commons/tableCardStyle';

const TableCardPagination = ({ ...props }) => {
  const { tablePaginationStore } = props;
  return (
    <TableFooter>
      <TableRow>
        <TablePagination
          rowsPerPageOptions={[10, 20, 50]}
          component="div"
          count={tablePaginationStore.page.total}
          rowsPerPage={tablePaginationStore.queryParam.pageSize}
          page={tablePaginationStore.page.pages - 1}
          backIconButtonProps={{
            'aria-label': 'Previous Page',
          }}
          nextIconButtonProps={{
            'aria-label': 'Next Page',
          }}
          onChangePage={(e, page) => tablePaginationStore.handleChangePage(e, page)}
          onChangeRowsPerPage={e => tablePaginationStore.handleChangeRowsPerPage(e)}
        />
      </TableRow>
    </TableFooter>
  );
};

const TableCard = ({ ...props }) => {
  const {
    classes, cardTitle, cardDescription, tableBody, tablePaginationStore,
  } = props;
  return (
    <GridContainer className={classes.tableContainer}>
      <GridItem xs={12} sm={12} md={12}>
        <Card>
          <CardHeader color="primary">
            <h4 className={classes.cardTitleWhite}>{cardTitle}</h4>
            <p className={classes.cardCategoryWhite}>
              {cardDescription}
            </p>
          </CardHeader>
          <CardBody>
            <Table
              tableHeaderColor="primary"
              tableHead={tablePaginationStore.tableHead}
              tableBody={tableBody}
              tablePagination={<TableCardPagination tablePaginationStore={tablePaginationStore} />}
            />
          </CardBody>
        </Card>
      </GridItem>
    </GridContainer>
  );
};

TableCard.defaultProps = {
  cardTitle: 'Title',
  cardDescription: 'This is comment',
  tableBody: (<div />),
};

TableCard.propTypes = {
  cardTitle: ReactPropTypes.string,
  cardDescription: ReactPropTypes.string,
  classes: ReactPropTypes.shape().isRequired,
  tableBody: ReactPropTypes.element,
  tablePaginationStore: PropTypes.observableObject.isRequired,
};

TableCardPagination.propTypes = {
  tablePaginationStore: PropTypes.observableObject.isRequired,
};

export default withStyles(tableCardStyle)(TableCard);
