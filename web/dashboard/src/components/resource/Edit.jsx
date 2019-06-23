import React from 'react';
import ReactPropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';
import {
  Grid, Modal, TextField, Typography, Paper,
} from '@material-ui/core';

// Custom component
import ButtonGroup from '../commons/ButtonGroup';

const styles = theme => ({
  paper: {
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    position: 'absolute',
    width: '50vw',
    height: '40vh',
    padding: theme.spacing(4),
    outline: 'none',
  },
  form: {
    marginTop: theme.spacing(2),
  },
  btnGroup: {
    textAlign: 'center',
    marginTop: theme.spacing(8),
  },
  btn: {
    margin: theme.spacing(1),
  },
});

const Edit = ({ ...props }) => {
  const {
    classes, handleModal, handleSubmit, modalOpen,
  } = props;
  return (
    <Modal open={modalOpen} onClose={() => handleModal(false)}>
      <Paper className={classes.paper}>
        <Typography variant="h5" id="modal-title">
          Create New Resource
        </Typography>
        <Grid container spacing={3} className={classes.form}>
          <Grid item xs={12} sm={6}>
            <TextField
              id="name"
              name="name"
              label="Name"
              margin="normal"
              autoFocus
            />
            <TextField
              id="description"
              name="description"
              label="Description"
              margin="normal"
              fullWidth
            />
          </Grid>
        </Grid>
        <ButtonGroup handleModal={handleModal} handleSubmit={handleSubmit} />
      </Paper>
    </Modal>
  );
};

Edit.propTypes = {
  classes: ReactPropTypes.shape().isRequired,
  handleModal: ReactPropTypes.func.isRequired,
  modalOpen: ReactPropTypes.bool.isRequired,
};

export default withStyles(styles)(Edit);
