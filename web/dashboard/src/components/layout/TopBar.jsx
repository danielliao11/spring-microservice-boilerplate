import React from 'react';
import ReactPropTypes from 'prop-types';

// @material-ui/core components
import { AppBar, Toolbar, Typography } from '@material-ui/core';
import withStyles from '@material-ui/core/styles/withStyles';

// static
import topBarStyle from '../../styles/jss/components/layout/topBarStyle';

const TopBar = ({ ...props }) => {
  const { classes } = props;
  return (
    <div className={classes.container}>
      <AppBar position="static" color="white">
        <Toolbar>
          <Typography variant="h5" className={classes.typography}>
            Dashboard
          </Typography>
        </Toolbar>
      </AppBar>
    </div>
  );
};

TopBar.propTypes = {
  classes: ReactPropTypes.shape().isRequired,
};

export default withStyles(topBarStyle)(TopBar);
