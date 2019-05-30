import { darkBlueColor, whiteColor } from '../../../../material_kit/assets/jss/material-kit-react';

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
    backgroundColor: 'rgba(216, 216, 216, 0.2)',
  },
  list: {
    marginTop: '',
  },
};

export default navbarStyle;
