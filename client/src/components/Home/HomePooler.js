import React, { Component } from "react";
import { Redirect, Switch } from "react-router";
import { Link, NavLink, Route } from "react-router-dom";
import { Nav, Navbar } from "react-bootstrap";
import Pickup from "../PoolerPages/Pickup";
import Delivery from "../PoolerPages/Delivery";
import Orders from "../PoolerPages/Orders";
import Messaging from "../PoolerPages/Messaging";
import ViewStore from '../AdminPages/ViewStore';
import SearchPool from "../SearchPool/SearchPool";
import Profile from "../NewProfilePage/NewProfile";
import Checkout from "../Checkout/Checkout";
import Logout from "../Account/Logout";
import logo from "../../images/cart.png";
import Items from "../Products/Items";

class HomePooler extends Component {
    constructor(props) {
        super(props);
        this.state = {
            key: 1,
            restaurantName: "",
        };
    }

    componentWillMount() {
        let userType = localStorage.getItem("userType")
            ? localStorage.getItem("userType")
            : null;
        console.log("userType");
        console.log(userType);

        if (userType !== null) {
            console.log("componentWillMount");
            const payload = {};
            payload.queryName = "GET_RESTAURANT_NAME";
            payload.arguments = [localStorage.getItem("userId")];
        }
    }

    render() {
        const redirectVar =
            localStorage.getItem("userType") === null ? (
                <Redirect to="/home" />
            ) : null;

        return (
            <div>

                <div style={styles.container}>
                    <div className='rowC'>
                        <div>
                            <img style={styles.logo} src={logo} alt="Quora" />
                        </div>
                        <div>
                            <h3 style={styles.message}>&nbsp;&nbsp;CartPool</h3>
                        </div>
                    </div>
                </div>

                <div>
                    <Navbar>
                        <Navbar.Brand as={Link} to="/"></Navbar.Brand>
                        <Nav>
                            <Nav.Link as={NavLink} to="/homePooler/">
                                HomePooler
                            </Nav.Link>
                            <Nav.Link as={NavLink} to="/homePooler/NewProfile/">
                                Profile
                            </Nav.Link>
                        </Nav>

                        <Nav className="ml-auto">
                            <Nav.Link as={NavLink} to='/homePooler/viewStore/'>Stores</Nav.Link>
                            <Nav.Link as={NavLink} to="/homePooler/pickup/">
                                Pickup
                            </Nav.Link>
                            <Nav.Link as={NavLink} to="/homePooler/delivery/">
                                Delivery
                            </Nav.Link>
                            <Nav.Link as={NavLink} to="/homePooler/orders/">
                                Orders
                            </Nav.Link>
                            <Nav.Link as={NavLink} to="/homePooler/messaging/">
                                Messaging
                            </Nav.Link>
                            <Nav.Link as={NavLink} to="/logout/">
                                Logout
                            </Nav.Link>
                        </Nav>
                    </Navbar>
                </div>

                <div>
                    <Switch>
                        <Route exact path='/homePooler/viewStore/' component={ViewStore} />
                        <Route exact path='/homePooler/item/' component={Items} />
                        <Route exact path="/homePooler/" component={SearchPool} />
                        <Route exact path="/homePooler/orders/" component={Orders} />
                        <Route exact path="/homePooler/pickup/" component={Pickup} />
                        <Route exact path="/homePooler/delivery/" component={Delivery} />
                        <Route exact path="/homePooler/checkout/" component={Checkout} />
                        <Route exact path="/homePooler/messaging/" component={Messaging} />
                        <Route exact path='/logout/' component={Logout} />
                        <Route exact path='/homePooler/NewProfile/' component={Profile} />
                    </Switch>
                </div>
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
        paddingTop: "15%"
    },
    logo: {
        paddingTop: "10px",
        width: "50px",
    },
    searchComponent: {}
}

export default HomePooler;
