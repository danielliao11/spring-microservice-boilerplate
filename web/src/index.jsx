import React from 'react';
import ReactDOM from 'react-dom';
import Container from './containers';

ReactDOM.render(
  <Container />,
  document.getElementById('app'),
);

if (process.env.NODE_ENV !== 'production') {
  if (module.hot) {
    module.hot.accept();
  }
}
