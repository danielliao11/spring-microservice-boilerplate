import React from 'react';
import ReactPropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import { Button, Modal, Typography } from '@material-ui/core';
import { PropTypes } from 'mobx-react';

const getModalStyle = () => {
  const top = 50;
  const left = 50;

  return {
    top: `${top}%`,
    left: `${left}%`,
    transform: `translate(-${top}%, -${left}%)`,
  };
};

const useStyles = makeStyles(theme => ({
  paper: {
    position: 'absolute',
    width: 400,
    backgroundColor: theme.palette.background.paper,
    boxShadow: theme.shadows[5],
    padding: theme.spacing(4),
    outline: 'none',
  },
}));

const Edit = ({ ...props }) => {
  const { store } = props;
  const [modalStyle] = React.useState(getModalStyle);
  const classes = useStyles();
  console.log(store.modalOpen);
  return (
    <Modal open={store.modalOpen}>
      <div style={modalStyle} className={classes.paper}>
        <Typography variant="h6" id="modal-title">
          Text in a modal
        </Typography>
        <Typography variant="subtitle1" id="simple-modal-description">
          Duis mollis, est non commodo luctus, nisi erat porttitor ligula.
        </Typography>
        <Button onClick={() => store.handleModal(false)}>Close</Button>
      </div>
    </Modal>
  );
};

Edit.propTypes = {
  store: PropTypes.observableObject.isRequired,
};

export default Edit;
