import { create } from 'mobx-persist';
import localForage from 'localforage';

const authForage = localForage.createInstance({
  name: 'authForage',
});

const resourceForage = localForage.createInstance({
  name: 'resourceForage',
});

const authHydrate = create({
  storage: authForage,
  jsonify: false,
});

const resourceHydrate = create({
  storage: resourceForage,
  jsonify: false,
});

export {
  authHydrate,
  resourceHydrate,
};
