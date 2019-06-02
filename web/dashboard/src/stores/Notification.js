import { observable, action, computed } from 'mobx';

export default class Notification {
  @observable mails = 0;

  @observable notifications = 0;

  @computed get getMails() {
    return this.mails;
  }

  @computed get getNotifications() {
    return this.notifications;
  }
}
