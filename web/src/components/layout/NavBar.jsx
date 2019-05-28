import React from 'react';
import { Link } from 'react-router';
import ReactPropTypes from 'prop-types';
import { observer, inject, PropTypes } from 'mobx-react';

// core components
import GridContainer from '../../material_kit/components/Grid/GridContainer';
import GridItem from '../../material_kit/components/Grid/GridItem';
import Card from '../../material_kit/components/Card/Card';

@observer
export default class NavBar extends React.Component {
  render() {
    return (
      <div>
        <GridContainer>
          <GridItem xs={12} sm={12} md={4}>

          </GridItem>
        </GridContainer>
      </div>
    );
  }
}