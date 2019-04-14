import { observable, action } from 'mobx';
import { persist } from 'mobx-persist';
import resource from '../utils/resource';

export default class Authorization {
  @observable @persist bearerToken = '';

  @observable cardAnimaton = '';

  @observable params = {
    email: '',
    password: '',
  };

  @observable hints = {
    email: '',
    password: '',
  }

  @observable error = {
    email: '',
    password: '',
  }

  @observable validated = '';

  @action.bound init() {
    this.params.email = '';
    this.params.password = '';
    this.cardAnimaton = 'cardHidden';
    this.hints.email = 'Email...';
    this.hints.password = 'Password...';
    this.error.email = false;
    this.error.password = false;
    this.validated = false;
  }

  @action.bound cardShow() {
    this.cardAnimaton = '';
  }


  @action.bound setParam(name, e) {
    this.params[name] = e.target.value;
  }

  @action.bound emailValidate(val) {
    val === '' && this.setHint('email', 'Email cannot be null');
    const regex = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    regex.test(val) ? this.validateSuccess() : this.setHint('email', 'Invalid email format.');
  }

  @action.bound passwordValidate(val) {
    val === '' && this.setHint('password', 'Password cannot be null');
    const regex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[a-zA-Z0-9]{8,16}$/;
    regex.test(val)
      ? this.validateSuccess()
      : this.setHint('password', 'Password must be between 8 to 16 characters, contain at least one number / lowercase letter / uppercase letter.');
  }

  @action.bound validateSuccess() {
    this.hints.email = 'success';
    this.hints.password = 'success';
    this.error.email = false;
    this.error.password = false;
    this.validated = true;
  }

  @action.bound setHint(name, msg) {
    this.hints[name] = msg;
    this.error[name] = true;
    this.validated = false;
  }

  @action.bound login() {
    this.emailValidate(this.params.email);
    this.passwordValidate(this.params.password);
    if (!this.validated) {
      return null;
    }
    const data = {
      usr: this.params.email,
      pwd: this.params.password,
    };
    return resource.post('/open/login', data);
  }

  @action.bound getToken() {
    if (this.bearerToken === '') {
      return process.env.BASIC_TOKEN;
    }
    return this.bearerToken;
  }

  @action.bound authorized() {
    return this.bearerToken !== '';
  }

  @action.bound setToken(res) {
    this.bearerToken = `${res.token_type} ${res.access_token}`;
  }

  @action.bound logout() {
    this.bearerToken = '';
  }
}
