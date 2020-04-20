import React, {Component} from 'react';
import {Redirect, Switch} from 'react-router';
import {Link, NavLink, Route} from "react-router-dom";
import {Nav, Navbar} from "react-bootstrap";
import Stores from '../InventoryManagement/Stores';

class HomeAdmin extends Component {
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
                            <Nav.Link as={NavLink} to='/homeOwner/orders/'>Orders</Nav.Link>
                            <Nav.Link as={NavLink} to='/homeOwner/menu/'>Menu</Nav.Link>
                        </Nav>
                        <Nav className="ml-auto">
                            <Nav.Link as={NavLink} to='/homeAdmin/store/'>Store</Nav.Link>
                        </Nav>
                    </Navbar>
                </div>


                <div>
                    <Switch>
                        <Route exact path='/homeAdmin/store/' component={Stores}/>
                    </Switch>
                </div>
            </div>
        );
    }
}


export default HomeAdmin;