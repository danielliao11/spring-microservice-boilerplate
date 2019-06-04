import { configure } from 'mobx';
import { enableLogging } from 'mobx-logger';
import Authorization from './Authorization';
import Notification from './Notification';
import Sign from './Sign';
import Configuration from './Configuration';

configure({ enforceActions: 'observed' });

enableLogging({
  predicate: () => process.env.NODE_ENV !== 'production',
  action: true,
  reaction: true,
  transaction: true,
  compute: true,
});

const authorization = new Authorization();
const notification = new Notification();
const sign = new Sign();
const configuration = new Configuration();

export {
  authorization,
  notification,
  sign,
  configuration,
};
