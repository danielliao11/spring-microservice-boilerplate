import React from 'react';
import { observer, inject, PropTypes } from 'mobx-react';
import { makeStyles } from '@material-ui/core/styles';
import { Button, Modal, Typography } from '@material-ui/core';

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

@inject('resource')
@observer
class Edit extends React.Component {
  render() {
    const { resource } = this.props;
    const [modalStyle] = React.useState(getModalStyle);
    const classes = useStyles();
    return (
      <Modal open={resource.modalOpen}>
        <div style={modalStyle} className={classes.paper}>
          <Typography variant="h6" id="modal-title">
            Text in a modal
          </Typography>
          <Typography variant="subtitle1" id="simple-modal-description">
            Duis mollis, est non commodo luctus, nisi erat porttitor ligula.
          </Typography>
          <Button onClick={() => resource.handleModal(false)}>Close</Button>
        </div>
      </Modal>
    );
  }
}

Edit.propTypes = {
  resource: PropTypes.observableObject.isRequired,
};

export default Edit;
