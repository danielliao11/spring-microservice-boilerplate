import theme from '../../theme';

const topBarStyle = {
  container: {
    width: '100%',
    position: 'absolute',
    minHeight: 64,
  },
  typography: {
    marginLeft: 30,
  },
  grow: {
    flexGrow: 1,
  },
  section: {
    display: 'none',
    [theme.breakpoints.up('md')]: {
      display: 'flex',
    },
  },
};

export default topBarStyle;
