/* eslint-disable global-require */
/* eslint-disable import/no-dynamic-require */
import React from 'react';
import ReactPropTypes from 'prop-types';
import { Link } from 'react-router-dom';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';
import {
  Avatar, Divider, List, ListItem, ListItemIcon, ListItemText, Paper, Typography,
} from '@material-ui/core';

// static
import sideBarStyle from '../../styles/jss/components/layout/sideBarStyle';
import logo from '../../asserts/imgs/react-logo.png';

const createIcon = (icon) => {
  const iconName = icon.replace(/Icon$/, '');
  const resolved = require(`@material-ui/icons/${iconName}`).default;
  if (!resolved) {
    throw Error(`Could not find @material-ui/icons/${iconName}`);
  }
  return React.createElement(resolved);
};

const SideBar = ({ ...props }) => {
  const { classes, routes } = props;
  return (
    <Paper className={classes.paper}>
      <div className={classes.logoContainer}>
        <Avatar src={logo} className={classes.logo} />
        <Typography variant="h6" className={classes.logoText}>
          Daniel Liao
        </Typography>
      </div>
      <Divider className={classes.divider} variant="middle" light />
      <List component="nav" className={classes.list}>
        {routes
          .filter(route => route.show)
          .map(route => (
            <Link to={route.path} className={classes.link} key={route.name}>
              <ListItem button className={classes.listItem} key={route.name}>
                <ListItemIcon className={classes.listItemIcon}>
                  {createIcon(route.iconName)}
                </ListItemIcon>
                <ListItemText primary={route.name} className={classes.liteItemText} />
              </ListItem>
            </Link>
          ))}
      </List>
    </Paper>
  );
};

SideBar.propTypes = {
  classes: ReactPropTypes.shape().isRequired,
  routes: ReactPropTypes.arrayOf(ReactPropTypes.shape()).isRequired,
};

export default withStyles(sideBarStyle)(SideBar);
