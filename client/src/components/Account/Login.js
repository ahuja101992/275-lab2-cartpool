import React, {Component} from "react";
//import logo from '../../static/images/login_twitter_logo.png';
import logo from "../../images/cart.png";
import {signIn, verifyEmail} from "../../redux/actions/authActions";
import {connect} from "react-redux";
import {Button, Form, Toast} from "react-bootstrap";
import {Redirect} from "react-router";
import FacebookLogin from "react-facebook-login";
import GoogleLogin from "react-google-login";
import "../../css/Account.css";

function mapStateToProps(store) {
    return {
        signinSuccess: store.auth.signinSuccess,
        signinMessage: store.auth.signinMessage,
        userActive: store.auth.userActive,
        verifyEmailSuccess: store.auth.verifyEmailSuccess,
        verifyEmailMessage: store.auth.verifyEmailMessage,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        signIn: (payload) => dispatch(signIn(payload)),
        verifyEmail: (payload) => dispatch(verifyEmail(payload)),
    };
}

class Login extends Component {
    constructor(props) {
        super(props);

        this.state = {
            showVerifyEmailBox: this.props.verifyEmailSuccess,
            redirectVar: false,
            date: null,
            email: null,
            accessToken: null,
            name: null,
            img_url: null,
            provider: null,
            provider_id: null,
            OAuthRedirect: false,
            firstName: null,
            lastName: null,
        };
    }

    signIn = (e) => {
        e.preventDefault();
        const data = {};
        for (let i = 0; i < e.target.length; i++) {
            if (e.target[i].id !== "") {
                data[e.target[i].id] = e.target[i].value;
            }
        }
        this.setState({date: new Date().getTime()});

        this.props.signIn(data);
    };

    login = (res, type) => {
        let data = {};
        if (type === "facebook" && res.email) {
            data = {
                email: res.email,
                accessToken: res.accessToken,
                name: res.name,
                img_url: res.picture.data.url,
                provider_id: res.id,
                provider: "facebook",
            };
        } else if (type === "google" && res.Qt.zu) {
            data = {
                email: res.Qt.zu,
                accessToken: res.accessToken,
                name: res.Qt.Ad,
                img_url: res.Qt.gL,
                provider_id: res.Qt.ZU,
                provider: "google",
            };
        }

        this.props.signIn(data);
    };

    signup = (res, type) => {
        if (type === "facebook" && res.email) {
            this.setState({
                email: res.email,
                accessToken: res.accessToken,
                name: res.name,
                img_url: res.picture.data.url,
                provider_id: res.id,
                provider: "facebook",
                OAuthRedirect: true,
            });
        } else if (type === "google" && res.Qt.zu) {
            this.setState({
                email: res.Qt.zu,
                accessToken: res.accessToken,
                name: res.Qt.Ad,
                firstName: res.Qt.DW,
                lastName: res.Qt.DU,
                img_url: res.Qt.gL,
                provider_id: res.Qt.ZU,
                provider: "google",
                OAuthRedirect: true,
            });
        }
    };

    isAdmin = () => {
        return "sjsu.edu" === localStorage.getItem("email").split("@")[1];
    };

    componentDidMount() {
        if (this.props.match.params.email !== undefined) {
            console.log(
                "this.props.match.params.email: " + this.props.match.params.email
            );

            const payload = {};
            payload.email = this.props.match.params.email;
            this.props.verifyEmail(payload);
        } else {
            console.log("undefined");
        }
    }

    render() {
        const responseFacebook = (response) => {
            console.log(" facebook", response);
            this.signup(response, "facebook");
            console.log(this.state);
        };
        const responseGoogle = (response) => {
            console.log(response);
            this.signup(response, "google");
        };
        const loginFaceook = (response) => {
            console.log("", response);
            this.login(response, "facebook");
        };
        const LoginGoogle = (response) => {
            console.log(response);
            this.login(response, "google");
        };

        return (
            <div style={styles.container}>
                {this.state.redirectVar === true && (
                    <Redirect
                        to={{
                            pathname: "/signup",
                        }}
                    />
                )}

                {this.props.signinSuccess === true &&
                localStorage.getItem("email") !== null &&
                this.isAdmin() && (
                    <Redirect
                        to={{
                            pathname: "/homeAdmin",
                        }}
                    />
                )}

                {this.props.signinSuccess === true &&
                localStorage.getItem("email") !== null &&
                !this.isAdmin() && (
                    <Redirect
                        to={{
                            pathname: "/homePooler",
                        }}
                    />
                )}

                {this.state.OAuthRedirect === true && (
                    <Redirect
                        to={{
                            pathname: "/signup",
                            props: {
                                email: this.state.email,
                                accessToken: this.state.accessToken,
                                name: this.state.name,
                                img_url: this.state.img_url,
                                provider_id: this.state.provider_id,
                                provider: this.state.provider,
                                firstName: this.state.firstName,
                                lastName: this.state.lastName,
                            },
                        }}
                    />
                )}

                {this.props.signinSuccess === false && (
                    <Toast>
                        <Toast.Header>
                            <img
                                src="holder.js/20x20?text=%20"
                                className="rounded mr-2"
                                alt=""
                            />
                            <strong className="mr-auto">Notification</strong>
                        </Toast.Header>
                        <Toast.Body>{this.props.signinMessage}</Toast.Body>
                    </Toast>
                )}

                {this.props.verifyEmailSuccess === true && (
                    <Toast
                        onClose={() => this.setState({showVerifyEmailBox: false})}
                        show={this.props.verifyEmailSuccess}
                    >
                        <Toast.Header>
                            <img
                                src="holder.js/20x20?text=%20"
                                className="rounded mr-2"
                                alt=""
                            />
                            <strong className="mr-auto">Notification</strong>
                        </Toast.Header>
                        <Toast.Body>{this.props.verifyEmailMessage}</Toast.Body>
                    </Toast>
                )}

                <div>
                    <img style={styles.logo} src={logo} alt="Quora"/>
                </div>
                <h3 style={styles.message}>Log in to CartPool</h3>
                <Form onSubmit={this.signIn}>
                    <div style={styles.email}>
                        <Form.Group controlId="email">
                            <Form.Label>Email</Form.Label>
                            <Form.Control placeholder="Enter your email" required/>
                        </Form.Group>
                    </div>

                    <div style={styles.email}>
                        <Form.Group controlId="password">
                            <Form.Label>Password</Form.Label>
                            <Form.Control
                                type="password"
                                placeholder="Enter your password"
                                required
                            />
                        </Form.Group>
                    </div>
                    <div>
                        <div>
                            <Button
                                style={styles.loginButton}
                                variant="primary"
                                type="submit"
                            >
                                Log in
                            </Button>
                        </div>
                        <div>
                            <FacebookLogin
                                appId="648035059374184"
                                autoLoad={false}
                                size="small"
                                textButton="&nbsp;&nbsp;Login with FaceBook"
                                fields="name,email,picture"
                                cssClass="btnFacebook"
                                icon="fab fa-facebook-f"
                                onClick={loginFaceook}
                                callback={loginFaceook}
                            />
                        </div>
                        <div>
                            <GoogleLogin
                                clientId="332159711982-5stv96v0qenutt5p3lrv0jtbo9lst47e.apps.googleusercontent.com"
                                buttonText="Login with Google "
                                className="btnGoogle"
                                onSuccess={LoginGoogle}
                                onFailure={LoginGoogle}
                                icon={false}
                                cookiePolicy={"single_host_origin"}
                            >
                                <i
                                    className="fa fa-google-plus"
                                    style={{marginLeft: "5px"}}
                                />
                                <span>&nbsp;&nbsp;Sign In with Google</span>
                            </GoogleLogin>
                        </div>
                    </div>

                    <div style={styles.signUpBox}>
                        <div>
                            <Form.Row className="d-flex justify-content-center mb-4">
                                <Form.Label
                                    style={{
                                        fontWeight: "bold",
                                        fontSize: 20,
                                    }}
                                >
                                    Or, New to CartPool?
                                </Form.Label>
                            </Form.Row>
                            <Form.Row>
                                <Button
                                    style={styles.signUpButton}
                                    variant="primary"
                                    onClick={() => this.setState({redirectVar: true})}
                                >
                                    Sign up
                                </Button>
                            </Form.Row>
                            <Form onSubmit={this.signUp}></Form>
                        </div>

                        <div>
                            <FacebookLogin
                                appId="648035059374184"
                                autoLoad={false}
                                size="small"
                                textButton="&nbsp;&nbsp;Signup with Facebook"
                                fields="name,email,picture"
                                cssClass="btnFacebook"
                                icon="fab fa-facebook-f"
                                onClick={responseFacebook}
                                callback={responseFacebook}
                            />
                        </div>
                        <div>
                            <GoogleLogin
                                clientId="332159711982-5stv96v0qenutt5p3lrv0jtbo9lst47e.apps.googleusercontent.com"
                                buttonText="Login with Google "
                                className="btnGoogle"
                                onSuccess={responseGoogle}
                                onFailure={responseGoogle}
                                icon={false}
                                cookiePolicy={"single_host_origin"}
                            >
                                <i
                                    className="fa fa-google-plus"
                                    style={{marginLeft: "5px"}}
                                />
                                <span>&nbsp;&nbsp;SignUp with Google</span>
                            </GoogleLogin>
                        </div>
                    </div>
                </Form>
            </div>
        );
    }
}

const styles = {
    container: {
        display: "flex",
        alignItems: "center",
        flexDirection: "column",
    },
    message: {
        fontWeight: "bold",
        paddingTop: "2rem",
    },
    logo: {
        paddingTop: "10px",
        width: "50px",
    },
    email: {
        width: "30rem",
    },
    loginButton: {
        width: "30rem",
        backgroundColor: "#2F99EA",
    },
    signUpBox: {
        marginTop: 40,
    },
    signUpButton: {
        backgroundColor: "#2F99EA",
        marginLeft: 5,
        marginRight: 5,
        width: "100%",
    },
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);
