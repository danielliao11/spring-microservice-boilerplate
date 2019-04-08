import { configure } from 'mobx';
import { enableLogging } from 'mobx-logger';
import Authorization from './Authorization';
import Login from './Login';

configure({ enforceActions: 'observed' });

enableLogging({
  predicate: () => process.env.NODE_ENV !== 'production',
  action: true,
  reaction: true,
  transaction: true,
  compute: true,
});

const authorization = new Authorization();
const login = new Login();

export {
  authorization,
  login,
};
