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

  @observable form = {
    id: 0,
    name: '',
    description: '',
    status: 0,
  }

  tableHeaderData = ['Name', 'Description', 'Status', 'CreatedAt']

  @observable page = {
    total: 0,
    pages: 1,
  }

  @observable content = []

  @observable modalOpen = false;

  @action.bound fetchPage() {
    const data = {
      param: this.queryParam,
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
    e.persist();
    this.queryParam[e.target.name] = e.target.value;
    this.fetchPage(this.queryParam);
  }

  @action.bound clearSearch() {
    this.queryParam = {
      name: '',
      status: '',
      pageNumber: 1,
      pageSize: 20,
      sortedBy: '',
    };
  }

  @action.bound handleModal(open) {
    this.modalOpen = open;
  }

  @action.bound handleSubmit() {
    if (this.form.id) {
      resource
        .put(`/management/resources${this.form.id}`, this.form)
        .then(() => this.fetchPage())
        .catch();
    } else {
      resource
        .post('/management/resources', this.form)
        .then(() => this.fetchPage())
        .catch();
    }
  }
}
