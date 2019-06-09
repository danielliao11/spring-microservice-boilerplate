import { observable, action } from 'mobx';
import resource from '../utils/resource';

export default class Resource {
  @observable queryParam = {
    pageNo: 1,
    pageSize: 10,
    sortedBy: '',
  }

  @action.bound fetchPage(queryParam) {
    const data = {
      param: queryParam,
    };
    resource
      .get('/management/resources/page', data)
      .then(this.fetchPageSuccess);
  }

  @action.bound fetchPageSuccess(res) {
    console.log(res);
  }
}
