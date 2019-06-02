import React from 'react';
import ReactPropTypes from 'prop-types';

// @material-ui/core components
import {
  AppBar,
  Badge,
  IconButton,
  Toolbar,
  Typography,
} from '@material-ui/core';
import {
  AccountCircle,
  Mail,
  Notifications,
} from '@material-ui/icons';
import withStyles from '@material-ui/core/styles/withStyles';

// custom components
import SearchBar from './SearchBar';

// static
import topBarStyle from '../../styles/jss/components/layout/topBarStyle';

const TopBar = ({ ...props }) => {
  const { classes, notification } = props;
  return (
    <div className={classes.container}>
      <AppBar position="static" color="default">
        <Toolbar>
          <Typography variant="h5" className={classes.typography}>
            Dashboard
          </Typography>
          <div className={classes.grow} />
          <SearchBar />
          <div className={classes.section}>
            <IconButton color="inherit">
              <Badge badgeContent={notification.getMails} invisible={notification.getMails === 0} color="secondary">
                <Mail />
              </Badge>
            </IconButton>
            <IconButton color="inherit">
              <Badge badgeContent={notification.getNotifications} invisible={notification.getNotifications === 0} color="secondary">
                <Notifications />
              </Badge>
            </IconButton>
            <IconButton color="inherit">
              <AccountCircle />
            </IconButton>
          </div>
        </Toolbar>
      </AppBar>
    </div>
  );
};

TopBar.propTypes = {
  classes: ReactPropTypes.shape().isRequired,
  notification: ReactPropTypes.shape().isRequired,
};

export default withStyles(topBarStyle)(TopBar);
