import { observable, action } from 'mobx';
import resource from '../utils/resource';

export default class Login {
  @observable username = '';

  @observable password = '';

  @action.bound login() {
    this.hints.usrErr = this.username === '' ? 'Username cannot be null' : '';
    this.hints.pwdErr = this.password === '' ? 'Password cannot be null' : '';
    const data = {
      usr: this.username,
      pwd: this.password,
      openid: 'management',
    };
    return resource
      .post('/open/login', data)
      .then(this.loginSuccess)
      .catch(this.loginFail);
  }
}
