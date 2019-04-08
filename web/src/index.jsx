import React from 'react';
import ReactDOM from 'react-dom';
import { MuiThemeProvider } from '@material-ui/core/styles';
import theme from './styles/theme';
import Container from './containers';

function App() {
  return (
    <MuiThemeProvider theme={theme}>
      <Container />
    </MuiThemeProvider>
  );
}

ReactDOM.render(<App />, document.querySelector('#app'));

if (process.env.NODE_ENV !== 'production') {
  if (module.hot) {
    module.hot.accept();
  }
}
