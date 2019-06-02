import { observable, action, computed } from 'mobx';
import { persist } from 'mobx-persist';
import cache from './Cache';

export default class Authorization {
  @observable @persist bearerToken = '';

  @observable cardAnimaton = '';

  @action.bound init() {
    this.cardAnimaton = 'cardHidden';
  }

  @action.bound cardShow() {
    this.cardAnimaton = '';
  }

  @computed get getToken() {
    if (this.bearerToken === '' || this.bearerToken.includes('undefined')) {
      return process.env.BASIC_TOKEN;
    }
    return this.bearerToken;
  }

  @computed get authorized() {
    return this.bearerToken !== '';
  }

  @action.bound setToken(res) {
    this.bearerToken = `${res.data.token_type} ${res.data.access_token}`;
    cache.setToken(`${res.data.token_type} ${res.data.access_token}`);
  }

  @action.bound logout() {
    this.bearerToken = '';
    cache.logout();
  }
}
