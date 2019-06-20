import React from 'react';
import ReactPropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import { Button, Modal, Typography } from '@material-ui/core';

const styles = theme => ({
  paper: {
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    position: 'absolute',
    width: 400,
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[5],
    padding: theme.spacing(4),
    outline: 'none',
  },
});

const Edit = ({ ...props }) => {
  const { classes, handleModal, modalOpen } = props;
  return (
    <Modal open={modalOpen}>
      <div className={classes.paper}>
        <Typography variant="h6" id="modal-title">
          Text in a modal
        </Typography>
        <Typography variant="subtitle1" id="simple-modal-description">
          Duis mollis, est non commodo luctus, nisi erat porttitor ligula.
        </Typography>
        <Button onClick={() => handleModal(false)}>Close</Button>
      </div>
    </Modal>
  );
};

Edit.propTypes = {
  classes: ReactPropTypes.shape().isRequired,
  handleModal: ReactPropTypes.func.isRequired,
  modalOpen: ReactPropTypes.bool.isRequired,
};

export default withStyles(styles)(Edit);
