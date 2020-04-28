import React, {Component} from 'react';
import {Redirect, Switch} from 'react-router';
import {Link, NavLink, Route} from "react-router-dom";
import {Nav, Navbar} from "react-bootstrap";
import Pickup from '../PoolerPages/Pickup';
import Delivery from '../PoolerPages/Delivery';
import Orders from '../PoolerPages/Orders';


class HomePooler extends Component {
    constructor(props) {
        super(props);
        this.state = {
            key: 1,
            restaurantName: ""
        };
    }

    componentWillMount() {
        let userType = localStorage.getItem('userType') ? localStorage.getItem('userType') : null;
        console.log("userType");
        console.log(userType);

        if (userType !== null) {
            console.log("componentWillMount");
            const payload = {};
            payload.queryName = "GET_RESTAURANT_NAME";
            payload.arguments = [localStorage.getItem('userId')];
        }
    }

    render() {
        const redirectVar = (localStorage.getItem('userType') === null) ? <Redirect to="/home"/> : null;

        return (
            <div>
                {/*{redirectVar}*/}

                {/*<div className="account-logo-container">*/}
                {/*    <img className="account-logo" src={logo} alt="Quora"/>*/}
                {/*</div>*/}
                <div>
                    <Navbar>
                        <Navbar.Brand as={Link} to='/'>{this.state.restaurantName}</Navbar.Brand>
                        <Nav>
                            <Nav.Link as={NavLink} to='/homePooler/'>HomePooler</Nav.Link>
                        </Nav>
                        <Nav className="ml-auto">
                            <Nav.Link as={NavLink} to='/homePooler/pickup/'>Pickup</Nav.Link>
                            <Nav.Link as={NavLink} to='/homePooler/delivery/'>Delivery</Nav.Link>
                            <Nav.Link as={NavLink} to='/homePooler/orders/'>Orders</Nav.Link>
                        </Nav>
                    </Navbar>
                </div>


                <div>
                    <Switch>
                        <Route exact path='/homePooler/' component={Pickup}/>
                        <Route exact path='/homePooler/orders/' component={Orders}/>
                        <Route exact path='/homePooler/pickup/' component={Pickup}/>
                        <Route exact path='/homePooler/delivery/' component={Delivery}/>
                    </Switch>
                </div>
            </div>
        );
    }
}


export default HomePooler;