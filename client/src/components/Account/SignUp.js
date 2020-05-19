import React, {Component} from "react";
import {Redirect} from "react-router";

import logo from "../../images/cart.png";
import "bootstrap/dist/css/bootstrap.min.css";
import {Button, Col, Form, Toast} from "react-bootstrap";
import {signUp} from "../../redux/actions/authActions";
import Expire from "./Expire";
import {connect} from "react-redux";

function mapStateToProps(store) {
    return {
        signupSuccess: store.auth.signupSuccess,
        signupMessage: store.auth.signupMessage,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        signUp: (payload) => dispatch(signUp(payload)),
    };
}

class SignUp extends Component {
    statesNamesArray = [
        "Alabama",
        "Alaska",
        "Arizona",
        "Arkansas",
        "California",
        "Colorado",
        "Connecticut",
        "Delaware",
        "District of Columbia",
        "Florida",
        "Georgia",
        "Hawaii",
        "Idaho",
        "Illinois",
        "Indiana",
        "Iowa",
        "Kansas",
        "Kentucky",
        "Louisiana",
        "Maine",
        "Montana",
        "Nebraska",
        "Nevada",
        "New Hampshire",
        "New Jersey",
        "New Mexico",
        "New York",
        "North Carolina",
        "North Dakota",
        "Ohio",
        "Oklahoma",
        "Oregon",
        "Maryland",
        "Massachusetts",
        "Michigan",
        "Minnesota",
        "Mississippi",
        "Missouri",
        "Pennsylvania",
        "Rhode Island",
        "South Carolina",
        "South Dakota",
        "Tennessee",
        "Texas",
        "Utah",
        "Vermont",
        "Virginia",
        "Washington",
        "West Virginia",
        "Wisconsin",
        "Wyoming",
    ];
    statesNames = new Set(
        this.statesNamesArray.map((state) => {
            return state.toLowerCase();
        })
    );
    stateCodesArray = [
        "AL",
        "AK",
        "AZ",
        "AR",
        "CA",
        "CO",
        "CT",
        "DE",
        "DC",
        "FL",
        "GA",
        "HI",
        "ID",
        "IL",
        "IN",
        "IA",
        "KS",
        "KY",
        "LA",
        "ME",
        "MT",
        "NE",
        "NV",
        "NH",
        "NJ",
        "NM",
        "NY",
        "NC",
        "ND",
        "OH",
        "OK",
        "OR",
        "MD",
        "MA",
        "MI",
        "MN",
        "MS",
        "MO",
        "PA",
        "RI",
        "SC",
        "SD",
        "TN",
        "TX",
        "UT",
        "VT",
        "VA",
        "WA",
        "WV",
        "WI",
        "WY",
    ];
    stateCodes = new Set(
        this.stateCodesArray.map((state) => {
            return state.toLowerCase();
        })
    );

    constructor(props) {
        super(props);
        this.signUp = this.signUp.bind(this);
        this.state = {
            redirectVar: false,
            test: false,
        };
    }

    signUp = (e) => {
        e.preventDefault();

        const data = {};
        for (let i = 0; i < e.target.length; i++) {
            if (e.target[i].id !== "") {
                data[e.target[i].id] = e.target[i].value;
            }
        }

        let updatedData = {};
        updatedData.screenName = data.screenName;
        updatedData.nickName = data.nickName;
        updatedData.screenName = data.screenName;
        updatedData.street = data.street;
        updatedData.city = data.city;
        updatedData.state = data.state;
        updatedData.zip = data.zip;

        if (
            this.props.location.props &&
            this.props.location.props.provider !== ""
        ) {
            updatedData.email = data.email;
            updatedData.accessToken = this.props.location.props.accessToken;
            updatedData.img_url = this.props.location.props.img_url;
            updatedData.name = this.props.location.props.name;
            updatedData.provider = this.props.location.props.provider;
            updatedData.provider_id = this.props.location.props.provider_id;
            updatedData.firstName = this.props.location.props.firstName;
            updatedData.lastName = this.props.location.props.lastName;
        } else {
            updatedData.email = data.email;
            updatedData.password = data.password;
            updatedData.firstName = data.firstName;
            updatedData.lastName = data.lastName;
        }

        if (!this.checkValidState(updatedData.state)) {
            this.setState({
                isAddressCorrect: false,
                isAddressCorrectMessage: "Invalid state in address",
            });
        } else if (!this.checkValidZipcode(updatedData.zip)) {
            this.setState({
                isAddressCorrect: false,
                isAddressCorrectMessage: "Invalid zipcode in address",
            });
        } else {
            this.setState({isAddressCorrect: true}, () =>
                this.props.signUp(updatedData)
            );
        }

        this.props.signUp(updatedData);
    };

    callbackFunction = (val) => {
        this.setState({redirectVar: val});
    };

    checkValidState = (state) => {
        return (
            this.statesNames.has(state.trim().toLowerCase()) ||
            this.stateCodes.has(state.trim().toLowerCase())
        );
    };

    checkValidZipcode = (zipcode) => {
        const regexFiveDigit = RegExp("^[0-9][0-9][0-9][0-9][0-9]$");
        const regexNineDigit = RegExp(
            "^[0-9][0-9][0-9][0-9][0-9]-[0-9][0-9][0-9][0-9]$"
        );
        return (
            regexFiveDigit.test(zipcode.trim().toLowerCase()) ||
            regexNineDigit.test(zipcode.trim().toLowerCase())
        );
    };

    render() {
        let OAuthRedirect = false;
        if (
            this.props.location.props &&
            this.props.location.props.provider !== ""
        ) {
            OAuthRedirect = true;
        }
        return (
            <div style={styles.container}>
                {this.state.redirectVar === true && (
                    <Redirect
                        to={{
                            pathname: "/login",
                        }}
                    />
                )}

                {this.props.signupSuccess === true && (
                    <Expire delay={5000} parentCallback={this.callbackFunction}>
                        <Toast>
                            <Toast.Header>
                                <img
                                    src="holder.js/20x20?text=%20"
                                    className="rounded mr-2"
                                    alt=""
                                />
                                <strong className="mr-auto">Notification</strong>
                            </Toast.Header>
                            <Toast.Body>
                                You have successfully signed-up! You are being redirected to the
                                login page in 5 seconds.
                            </Toast.Body>
                        </Toast>
                    </Expire>
                )}

                {this.state.isAddressCorrect !== null &&
                this.state.isAddressCorrect === false && (
                    <Toast>
                        <Toast.Header>
                            <img
                                src="holder.js/20x20?text=%20"
                                className="rounded mr-2"
                                alt=""
                            />
                            <strong className="mr-auto">Notification</strong>
                        </Toast.Header>
                        <Toast.Body>{this.state.isAddressCorrectMessage}</Toast.Body>
                    </Toast>
                )}

                <div>
                    <img style={styles.logo} src={logo} alt="Quora"/>
                </div>

                <h3 style={styles.message}>SignUp</h3>
                <Form onSubmit={this.signUp}>
                    <Form.Group controlId="screenName">
                        <Form.Label>Screen name</Form.Label>
                        <Form.Control placeholder="Enter a cool screen name" required/>
                    </Form.Group>

                    <Form.Group controlId="nickName">
                        <Form.Label>Nickname</Form.Label>
                        <Form.Control placeholder="Enter a cool nickname" required/>
                    </Form.Group>

                    <Form.Row>
                        <Form.Group as={Col} controlId="firstName">
                            <Form.Label>First Name</Form.Label>
                            <Form.Control
                                defaultValue={
                                    this.props.location.props &&
                                    this.props.location.props.firstName !== ""
                                        ? this.props.location.props.firstName
                                        : ""
                                }
                                placeholder="Please provide your first name"
                                required
                            />
                        </Form.Group>

                        <Form.Group as={Col} controlId="lastName">
                            <Form.Label>Last Name</Form.Label>
                            <Form.Control
                                defaultValue={
                                    this.props.location.props &&
                                    this.props.location.props.lastName !== ""
                                        ? this.props.location.props.lastName
                                        : ""
                                }
                                placeholder="Please provide your last name"
                                required
                            />
                        </Form.Group>
                    </Form.Row>

                    <Form.Group controlId="street">
                        <Form.Label>Street</Form.Label>
                        <Form.Control placeholder="Street Address" required/>
                    </Form.Group>

                    <Form.Row>
                        <Form.Group as={Col} controlId="city">
                            <Form.Label>City</Form.Label>
                            <Form.Control placeholder="City name" required/>
                        </Form.Group>
                        <Form.Group as={Col} controlId="state">
                            <Form.Label>State</Form.Label>
                            <Form.Control placeholder="State name or code" required/>
                        </Form.Group>
                        <Form.Group as={Col} controlId="zip">
                            <Form.Label>Zipcode</Form.Label>
                            <Form.Control placeholder="12345" required/>
                        </Form.Group>
                    </Form.Row>

                    <Form.Group controlId="email">
                        <Form.Label>Email</Form.Label>
                        <Form.Control
                            disabled={
                                this.props.location.props &&
                                this.props.location.props.provider !== ""
                            }
                            defaultValue={
                                this.props.location.props &&
                                this.props.location.props.provider !== ""
                                    ? this.props.location.props.email
                                    : ""
                            }
                            placeholder={
                                this.props.location.props &&
                                this.props.location.props.provider !== ""
                                    ? this.props.location.props.email
                                    : "What's your email?"
                            }
                            required
                            type="email"
                        />
                    </Form.Group>

                    <Form.Group
                        controlId="password"
                        className={
                            this.props.location.props &&
                            this.props.location.props.provider !== ""
                                ? "d-none"
                                : ""
                        }
                    >
                        <Form.Label>Password</Form.Label>
                        <Form.Control
                            type="password"
                            placeholder="Enter a strong password"
                        />
                    </Form.Group>
                    {OAuthRedirect}
                    <Button style={styles.signUpButton} variant="primary" type="submit">
                        Sign up
                    </Button>
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
    signUpButton: {
        width: "30rem",
        backgroundColor: "#2F99EA",
    },
};

export default connect(mapStateToProps, mapDispatchToProps)(SignUp);
