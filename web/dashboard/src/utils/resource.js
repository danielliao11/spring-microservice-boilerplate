import axios from 'axios';
import cache from '../stores/Cache';

const resource = axios.create({
  baseURL: `${process.env.DEV_SERVER_PROXY}/${process.env.API_PATH}/${process.env.API_VERSION}`,
});

resource.interceptors.request.use(
  (config) => {
    // eslint-disable-next-line no-param-reassign
    config.headers.common.Authorization = cache.getToken();
    return config;
  },
  error => Promise.reject(error),
);

resource.interceptors.response.use(
  response => Promise.resolve(response),
  (error) => {
    if (error && error.status === 401) {
      cache.logout();
    }
    return Promise.reject(error);
  },
);

export default resource;
