import React from 'react';
import ReactPropTypes from 'prop-types';
import { PropTypes } from 'mobx-react';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';

// core components
import Card from '../../material_kit/components/Card/Card';
import CardBody from '../../material_kit/components/Card/CardBody';
import CardHeader from '../../material_kit/components/Card/CardHeader';
import GridContainer from '../../material_kit/components/Grid/GridContainer';
import GridItem from '../../material_kit/components/Grid/GridItem';
import Table from './Table';
import TableToolBar from './TableToolBar';
import TableCardPagination from './TableCardPagination';

// static
import tableCardStyle from '../../styles/jss/components/commons/tableCardStyle';

const TableCard = ({ ...props }) => {
  const {
    classes, cardTitle, cardDescription, tableBody, store,
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
            <TableToolBar store={store} />
            <Table
              tableHeaderColor="primary"
              tableHeaderData={store.tableHeaderData}
              tableBody={tableBody}
              tablePagination={<TableCardPagination tableStore={store} />}
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
  store: PropTypes.observableObject.isRequired,
};

export default withStyles(tableCardStyle)(TableCard);
