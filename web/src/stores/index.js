import { configure } from 'mobx';
import { enableLogging } from 'mobx-logger';

configure({ enforceActions: true });

enableLogging({
  predicate: () => process.env.NODE_ENV !== 'production',
  action: true,
  reaction: true,
  transaction: true,
  compute: true,
});
