import React from 'react';
import ReactPropTypes from 'prop-types';

// @material-ui/core components
import InputBase from '@material-ui/core/InputBase';
import SearchIcon from '@material-ui/icons/Search';
import withStyles from '@material-ui/core/styles/withStyles';

// static
import searchBarStyle from '../../styles/jss/components/layout/searchBarStyle';

const SearchBar = ({ ...props }) => {
  const { classes } = props;
  return (
    <div className={classes.search}>
      <div className={classes.searchIcon}>
        <SearchIcon />
      </div>
      <InputBase
        placeholder="Search…"
        classes={{
          root: classes.inputRoot,
          input: classes.inputInput,
        }}
      />
    </div>
  );
};

SearchBar.propTypes = {
  classes: ReactPropTypes.shape().isRequired,
};

export default withStyles(searchBarStyle)(SearchBar);
