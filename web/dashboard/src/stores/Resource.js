import { observable, action } from 'mobx';
import resource from '../utils/resource';

export default class Resource {
  @observable queryParam = {
    name: '',
    status: '',
    pageNumber: 1,
    pageSize: 20,
    sortedBy: '',
  }

  tableHeaderData = ['Name', 'Description', 'Status', 'CreatedAt']

  @observable page = {
    total: 0,
    pages: 1,
  }

  @observable content = []

  @action.bound fetchPage(queryParam) {
    const data = {
      param: queryParam,
    };
    // resource
    //   .get('/management/resources/page', data)
    //   .then(this.fetchPageSuccess);
  }

  @action.bound fetchPageSuccess(res) {
    this.page = res.data;
    this.content = res.data.list;
  }

  @action.bound handleChangeRowsPerPage(e) {
    this.queryParam.pageSize = e.target.value;
    this.fetchPage(this.queryParam);
  }

  @action.bound handleChangePage(e, page) {
    this.queryParam.pageNumber = page;
    this.fetchPage(this.queryParam);
  }

  @action.bound handParamSearch(e) {
    console.log(e);
  }
}
