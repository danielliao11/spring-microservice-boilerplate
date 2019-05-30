import React from 'react';
import ReactPropTypes from 'prop-types';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';
import {
  Avatar,
  Divider,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Paper,
} from '@material-ui/core';

// @material-ui/icons
import { asyncComponent } from 'react-async-component';
// static
import routes from '../../routers';
import navbarStyle from '../../styles/jss/components/layout/navbar';
import logo from '../../asserts/imgs/react-logo.png';

const createIconAsync = (icon) => {
  const iconName = icon.replace(/Icon$/, '');
  return React.createElement(asyncComponent({
    resolve: () => import(`@material-ui/icons/${iconName}`),
  }));
};

const NavBar = ({ ...props }) => {
  const { classes } = props;
  return (
    <Paper className={classes.paper}>
      <div className={classes.logoContainer}>
        <Avatar src={logo} className={classes.logo} />
        <div className={classes.logoText}>
          Daniel Liao
        </div>
      </div>
      <Divider className={classes.divider} variant="middle" light />
      <List component="nav">
        {routes
          .filter(route => route.show)
          .map(route => (
            <ListItem button>
              <ListItemIcon>
                {createIconAsync(route.iconName)}
              </ListItemIcon>
              <ListItemText primary={route.name} />
            </ListItem>
          ))}
      </List>
    </Paper>
  );
};

NavBar.propTypes = {
  classes: ReactPropTypes.shape().isRequired,
};

export default withStyles(navbarStyle)(NavBar);
