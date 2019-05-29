import React from 'react';
import ReactPropTypes from 'prop-types';
import { observer } from 'mobx-react';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';
import {
  Avatar,
  GridList,
  GridListTileBar,
  Paper,
} from '@material-ui/core/Avatar';

// custom components
import navbarStyle from '../../styles/jss/components/layout/navbar';

// static
// import routes from '../../routers';
import logo from '../../asserts/imgs/react-logo.png';

@observer
class NavBar extends React.Component {
  render() {
    const { classes } = this.props;
    return (
      <Paper className={classes.paper}>
        <Avatar src={logo} className={classes.logo} />
        {/* <GridList col="2">
          {routes
            .filter(route => route.show)
            .map(route => (
              <GridListTileBar
                title={route.name}
              />
            ))}
        </GridList> */}
      </Paper>
    );
  }
}

NavBar.propTypes = {
  classes: ReactPropTypes.shape().isRequired,
};

export default withStyles(navbarStyle)(NavBar);
