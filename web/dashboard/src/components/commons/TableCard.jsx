import React from 'react';
import ReactPropTypes from 'prop-types';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';

// core components
import Card from '../../material_kit/components/Card/Card';
import CardBody from '../../material_kit/components/Card/CardBody';
import CardHeader from '../../material_kit/components/Card/CardHeader';
import GridContainer from '../../material_kit/components/Grid/GridContainer';
import GridItem from '../../material_kit/components/Grid/GridItem';
import Table from './Table';

// static
import tableCardStyle from '../../styles/jss/components/commons/tableCardStyle';

const TableCard = ({ ...props }) => {
  const {
    classes,
    cardTitle,
    cardComment,
    tableHead,
    tableBody,
    tablePagination,
  } = props;
  return (
    <GridContainer className={classes.tableContainer}>
      <GridItem xs={12} sm={12} md={12}>
        <Card>
          <CardHeader color="primary">
            <h4 className={classes.cardTitleWhite}>{cardTitle}</h4>
            <p className={classes.cardCategoryWhite}>
              {cardComment}
            </p>
          </CardHeader>
          <CardBody>
            <Table
              tableHeaderColor="primary"
              tableHead={tableHead}
              tableBody={tableBody}
              tablePagination={tablePagination}
            />
          </CardBody>
        </Card>
      </GridItem>
    </GridContainer>
  );
};

TableCard.defaultProps = {
  cardTitle: 'Title',
  cardComment: 'This is comment',
  tableHead: [],
  tableBody: (<div />),
  tablePagination: (<div />),
};

TableCard.propTypes = {
  cardTitle: ReactPropTypes.string,
  cardComment: ReactPropTypes.string,
  classes: ReactPropTypes.shape().isRequired,
  tableHead: ReactPropTypes.arrayOf(ReactPropTypes.string),
  tableBody: ReactPropTypes.element,
  tablePagination: ReactPropTypes.element,
};

export default withStyles(tableCardStyle)(TableCard);
