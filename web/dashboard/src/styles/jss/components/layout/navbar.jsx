import { darkBlueColor, whiteColor } from '../../../../material_kit/assets/jss/material-kit-react';

const hoverColor = 'rgba(216, 216, 216, 0.2)';

const navbarStyle = {
  paper: {
    width: 218,
    height: '100%',
    display: 'flex',
    flexDirection: 'column',
    backgroundColor: darkBlueColor,
    borderRadius: 0,
  },
  logoContainer: {
    display: 'flex',
    width: '100%',
  },
  logo: {
    margin: '19px 22px 0 22px',
    width: 40,
    height: 40,
  },
  logoText: {
    marginTop: 29,
    width: '100%',
    fontSize: 18,
    color: whiteColor,
  },
  divider: {
    margin: '20px 0 0 0',
    backgroundColor: hoverColor,
  },
  list: {
    marginTop: 30,
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
    marginRight: 0,
    color: whiteColor,
  },
  liteItemText: {
    marginTop: 3,
    padding: '0 12px',
    '&>span': {
      color: whiteColor,
    },
  },
};

export default navbarStyle;
