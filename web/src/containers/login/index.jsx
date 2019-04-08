import React from 'react';
import ReactPropTypes from 'prop-types';
import { observer, inject, PropTypes } from 'mobx-react';
import CssBaseline from '@material-ui/core/CssBaseline';
import Paper from '@material-ui/core/Paper';

const styles = theme => ({
  main: {

  },
});

@inject('login')
@observer
export default class Login extends React.Component {
  static propTypes = {
    history: ReactPropTypes.shape({
      push: ReactPropTypes.func,
    }).isRequired,
    login: PropTypes.observableObject.isRequired,
  }

  render() {
    return (
      <main>
        <CssBaseline />
        <Paper>

        </Paper>
      </main>
    );
  }
}
