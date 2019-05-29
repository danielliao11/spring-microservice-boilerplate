import React from 'react';
import ReactPropTypes from 'prop-types';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';
import {
  Avatar,
  List,
  ListItem,
  ListItemText,
  Paper,
} from '@material-ui/core';

// static
import routes from '../../routers';
import navbarStyle from '../../styles/jss/components/layout/navbar';
import logo from '../../asserts/imgs/react-logo.png';

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
      <List component="nav">
        {routes
          .filter(route => route.show)
          .map(route => (
            <ListItem button>
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
