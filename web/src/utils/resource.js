import axios from 'axios';
import { authorization } from '../stores';

const resource = axios.create({
  baseURL: `${process.env.DEV_SERVER_PROXY}/${process.env.API_PATH}/${process.env.API_VERSION}`,
});

resource.interceptors.request.use(
  (config) => {
    // eslint-disable-next-line no-param-reassign
    config.headers.common.Authorization = authorization.token;
    return config;
  },
  error => Promise.reject(error),
);

resource.interceptors.response.use(
  response => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      authorization.authFailed();
    }
    return Promise.reject(error);
  },
);

export default resource;
