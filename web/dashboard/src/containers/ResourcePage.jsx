import React from 'react';
import ReactPropTypes from 'prop-types';
import { observer, inject, PropTypes } from 'mobx-react';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';

// core components
import Button from '../material_kit/components/CustomButtons/Button';
import Card from '../material_kit/components/Card/Card';
import CardBody from '../material_kit/components/Card/CardBody';
import CardHeader from '../material_kit/components/Card/CardHeader';
import GridContainer from '../material_kit/components/Grid/GridContainer';
import GridItem from '../material_kit/components/Grid/GridItem';
import Table from '../components/commons/Table';

// static
import resourcePageStyle from '../styles/jss/containers/resourcePageStyle';

const btn = () => (
  <Button
    justIcon
    href="#pablo"
    target="_blank"
    color="transparent"
    onClick={e => e.preventDefault()}
  >
    <i className="fab fa-twitter" />
  </Button>
);

class ResourcePage extends React.Component {
  render() {
    const { classes } = this.props;
    return (
      <GridContainer className={classes.tableContainer}>
        <GridItem xs={12} sm={12} md={12}>
          <Card>
            <CardHeader color="primary">
              <h4 className={classes.cardTitleWhite}>Simple Table</h4>
              <p className={classes.cardCategoryWhite}>
                Here is a subtitle for this table
              </p>
            </CardHeader>
            <CardBody>
              <Table
                tableHeaderColor="primary"
                tableHead={['Name', 'Country', 'City', 'Salary', 'Operation']}
                tableData={[
                  ['Dakota Rice', 'Niger', 'Oud-Turnhout', '$36,738'],
                  ['Minerva Hooper', 'Curaçao', 'Sinaai-Waas', '$23,789'],
                  ['Sage Rodriguez', 'Netherlands', 'Baileux', '$56,142'],
                  ['Philip Chaney', 'Korea, South', 'Overland Park', '$38,735'],
                  ['Doris Greene', 'Malawi', 'Feldkirchen in Kärnten', '$63,542'],
                  ['Mason Porter', 'Chile', 'Gloucester', '$78,615']
                ]}
              />
            </CardBody>
          </Card>
        </GridItem>
      </GridContainer>
    );
  }
}

ResourcePage.propTypes = {
  classes: ReactPropTypes.shape().isRequired,
};

export default withStyles(resourcePageStyle)(ResourcePage);
