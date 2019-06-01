import { blackColor, whiteColor } from '../../../../material_kit/assets/jss/material-kit-react';
import bgImg from '../../../../asserts/imgs/sidebar.jpg';

const dividerColor = 'rgba(216, 216, 216, 0.2)';
const hoverColor = 'rgba(156, 39, 176, 1)';

const sideBarStyle = {
  paper: {
    width: 260,
    height: '100%',
    display: 'flex',
    flexDirection: 'column',
    borderRadius: 0,
    backgroundImage: `url(${bgImg})`,
    backgroundSize: 'cover',
    backgroundPosition: 'center center',
    '&:after': {
      top: 0,
      left: 0,
      content: '""',
      width: 260,
      height: '100%',
      zIndex: 1,
      backgroundColor: blackColor,
      opacity: 0.8,
      display: 'block',
      position: 'absolute',
    },
  },
  logoContainer: {
    display: 'flex',
    width: '100%',
    zIndex: 3,
  },
  logo: {
    margin: '22px 14px 0 20px',
    width: 32,
    height: 32,
  },
  logoText: {
    marginTop: 24,
    width: '100%',
    color: whiteColor,
  },
  divider: {
    margin: '20px 0 0 0',
    backgroundColor: dividerColor,
    zIndex: 3,
  },
  list: {
    marginTop: 20,
    zIndex: 3,
  },
  listItem: {
    '&:hover': {
      backgroundColor: hoverColor,
    },
    '&:focus': {
      backgroundColor: hoverColor,
    },
  },
  listItemIcon: {
    margin: '0 0 0 12px',
    color: whiteColor,
  },
  liteItemText: {
    marginTop: 3,
    padding: '0 15px',
    '&>span': {
      color: whiteColor,
    },
  },
};

export default sideBarStyle;
