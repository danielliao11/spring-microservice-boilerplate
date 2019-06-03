import { observable, action } from 'mobx';
import resource from '../utils/resource';

export default class Configuration {
  @observable navs = [];

  @action.bound fetchConfigurations() {
    resource
      .get('/open/configurations')
      .then(this.fetchSuccess);
  }

  @action.bound fetchSuccess(res) {
    this.navs = res.data.navs;
  }
}
