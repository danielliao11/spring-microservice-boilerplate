import { observable, action } from 'mobx';
import resource from '../utils/resource';

export default class Sign {
  @observable validated = '';

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

  @action.bound init() {
    this.params.email = '';
    this.params.password = '';
    this.hints.email = 'Email...';
    this.hints.password = 'Password...';
    this.error.email = false;
    this.error.password = false;
    this.validated = false;
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

  @action.bound signIn() {
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
}
