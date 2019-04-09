import { observable } from 'mobx';
import { persist } from 'mobx-persist';

export default class Authorization {
  @observable @persist token = null;
}
