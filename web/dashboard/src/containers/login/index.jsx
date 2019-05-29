import React from 'react';
import ReactPropTypes from 'prop-types';
import { observer, inject, PropTypes } from 'mobx-react';

// @material-ui/core components
import withStyles from '@material-ui/core/styles/withStyles';
import InputAdornment from '@material-ui/core/InputAdornment';

// @material-ui/icons
import Email from '@material-ui/icons/Email';
import Lock from '@material-ui/icons/Lock';

// core components
import Header from '../../material_kit/components/Header/Header';
import HeaderLinks from '../../material_kit/components/Header/HeaderLinks';
import Footer from '../../material_kit/components/Footer/Footer';
import GridContainer from '../../material_kit/components/Grid/GridContainer';
import GridItem from '../../material_kit/components/Grid/GridItem';
import Button from '../../material_kit/components/CustomButtons/Button';
import Card from '../../material_kit/components/Card/Card';
import CardBody from '../../material_kit/components/Card/CardBody';
import CardHeader from '../../material_kit/components/Card/CardHeader';
import CardFooter from '../../material_kit/components/Card/CardFooter';
import CustomInput from '../../material_kit/components/CustomInput/CustomInput';

import bgImg from '../../asserts/imgs/bg.jpg';
import loginPageStyle from '../../styles/jss/containers/loginPageStyle';

@inject('authorization')
@observer
class LoginPage extends React.Component {
  componentWillMount() {
    const { authorization } = this.props;
    authorization.init();
  }

  componentDidMount() {
    const { authorization } = this.props;
    setTimeout(() => authorization.cardShow(), 700);
  }

  handleLogin() {
    const { history, authorization } = this.props;
    authorization.login()
      .then((res) => {
        authorization.setToken(res);
        history.push('/dashboard');
      });
  }

  render() {
    const { classes, authorization, ...rest } = this.props;
    return (
      <div>
        <Header
          absolute
          color="transparent"
          brand="Spring Microservice Boilerplate"
          rightLinks={<HeaderLinks />}
          {...rest}
        />
        <div
          className={classes.pageHeader}
          style={{
            backgroundImage: `url(${bgImg})`,
            backgroundSize: 'cover',
            backgroundPosition: 'top center',
          }}
        >
          <div className={classes.container}>
            <GridContainer justify="center">
              <GridItem xs={12} sm={12} md={4}>
                <Card className={classes[authorization.cardAnimaton]}>
                  <CardHeader color="primary" className={classes.cardHeader}>
                    <h3>Login</h3>
                    <div className={classes.socialLine}>
                      <Button
                        justIcon
                        href="#pablo"
                        target="_blank"
                        color="transparent"
                        onClick={e => e.preventDefault()}
                      >
                        <i className="fab fa-twitter" />
                      </Button>
                      <Button
                        justIcon
                        href="#pablo"
                        target="_blank"
                        color="transparent"
                        onClick={e => e.preventDefault()}
                      >
                        <i className="fab fa-facebook" />
                      </Button>
                      <Button
                        justIcon
                        href="#pablo"
                        target="_blank"
                        color="transparent"
                        onClick={e => e.preventDefault()}
                      >
                        <i className="fab fa-google-plus-g" />
                      </Button>
                    </div>
                  </CardHeader>
                  <p className={classes.divider}>Welcome</p>
                  <CardBody>
                    <CustomInput
                      labelText={authorization.hints.email}
                      id="email"
                      error={authorization.error.email}
                      success={authorization.hints.email === 'success'}
                      formControlProps={{
                        fullWidth: true,
                      }}
                      inputProps={{
                        type: 'email',
                        value: authorization.email,
                        onChange: e => authorization.setParam('email', e),
                        endAdornment: (
                          <InputAdornment position="end">
                            <Email className={classes.inputIconsColor} />
                          </InputAdornment>
                        ),
                      }}
                    />
                    <CustomInput
                      labelText={authorization.hints.password}
                      id="pass"
                      error={authorization.error.password}
                      success={authorization.hints.password === 'success'}
                      formControlProps={{
                        fullWidth: true,
                      }}
                      inputProps={{
                        type: 'password',
                        value: authorization.password,
                        onChange: e => authorization.setParam('password', e),
                        endAdornment: (
                          <InputAdornment position="end">
                            <Lock className={classes.inputIconsColor} />
                          </InputAdornment>
                        ),
                      }}
                    />
                  </CardBody>
                  <CardFooter className={classes.cardFooter}>
                    <Button simple color="primary" size="lg" onClick={() => this.handleLogin()}>
                      Get started
                    </Button>
                  </CardFooter>
                </Card>
              </GridItem>
            </GridContainer>
          </div>
          <Footer whiteFont />
        </div>
      </div>
    );
  }
}

LoginPage.propTypes = {
  classes: ReactPropTypes.shape().isRequired,
  history: ReactPropTypes.shape({
    push: ReactPropTypes.func,
  }).isRequired,
};

LoginPage.wrappedComponent.propTypes = {
  authorization: PropTypes.observableObject.isRequired,
};

export default withStyles(loginPageStyle)(LoginPage);
