const cache = {
  token: '',

  setToken(val) {
    this.token = val;
  },

  getToken() {
    return this.token === '' ? process.env.BASIC_TOKEN : this.token;
  },

  logout() {
    this.token = '';
  },
};

export default cache;
