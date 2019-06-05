import Home from '../containers/HomePage';
import Resource from '../containers/ResourcePage';

const routes = [
  {
    show: true,
    name: 'HOME',
    iconName: 'Home',
    path: '/home',
    component: Home,
  },
  {
    show: true,
    name: 'RESOURCE',
    iconName: 'LocalFlorist',
    path: '/resource',
    component: Resource,
  },
];

export default routes;
