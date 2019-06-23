import React from 'react';
import ReactPropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import Button from '@material-ui/core/Button';

const styles = theme => ({
  btnGroup: {
    textAlign: 'center',
    marginTop: theme.spacing(8),
  },
  btn: {
    margin: theme.spacing(1),
  },
});

const ButtonGroup = ({ ...props }) => {
  const { classes, handleModal, handleSubmit } = props;
  return (
    <div className={classes.btnGroup}>
      <Button variant="contained" color="primary" className={classes.btn} onClick={() => handleSubmit()}>
        Submit
      </Button>
      <Button variant="contained" color="secondary" className={classes.btn} onClick={() => handleModal(false)}>
        Cancel
      </Button>
    </div>
  );
};

ButtonGroup.propTypes = {
  classes: ReactPropTypes.shape().isRequired,
  handleModal: ReactPropTypes.func.isRequired,
  handleSubmit: ReactPropTypes.func.isRequired,
};

export default withStyles(styles)(ButtonGroup);
