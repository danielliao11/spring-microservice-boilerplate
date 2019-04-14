import { configure } from 'mobx';
import { enableLogging } from 'mobx-logger';
import Authorization from './Authorization';

configure({ enforceActions: 'observed' });

enableLogging({
  predicate: () => process.env.NODE_ENV !== 'production',
  action: true,
  reaction: true,
  transaction: true,
  compute: true,
});

const authorization = new Authorization();

export {
  authorization,
};
