import React from 'react';
import ReactPropTypes from 'prop-types';
import { Modal } from '@material-ui/core';
import { PropTypes } from 'mobx-react';

const Edit = ({ ...props }) => {
  const { store } = props;
  return (
    <Modal open>
      <div></div>
    </Modal>
  );
};

Edit.propTypes = {
  store: PropTypes.observableObject.isRequired,
};

export default Edit;
