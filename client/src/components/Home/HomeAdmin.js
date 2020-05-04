import React, {Component} from 'react';
import {Redirect, Switch} from 'react-router';
import {Link, NavLink, Route} from "react-router-dom";
import {Nav, Navbar} from "react-bootstrap";
import Stores from '../AdminPages/Stores';
import Items from '../Products/Items';
import AddItem from '../AdminPages/AddItem';
import Logout from "../Account/Logout";
import logo from "../../images/cart.png";
import '../../css/Account.css'
import ViewStore from '../AdminPages/ViewStore';

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

                <div style={styles.container}>
                    <div className='rowC'>
                        <div>
                            <img style={styles.logo} src={logo} alt="Quora"/>
                        </div>
                        <div>
                            <h3 style={styles.message}>&nbsp;&nbsp;CartPool</h3>
                        </div>
                    </div>
                </div>


                <div>
                    <Navbar>
                        <Navbar.Brand as={Link} to='/'></Navbar.Brand>
                        <Nav>
                        </Nav>
                        <Nav className="ml-auto">
                            <Nav.Link as={NavLink} to='/homeAdmin/viewStore/'>Stores</Nav.Link>
                            <Nav.Link as={NavLink} to='/homeAdmin/viewProduct/'>Products</Nav.Link>
                            {/* <Nav.Link as={NavLink} to='/homeAdmin/store/'>Store</Nav.Link> */}
                            {/* <Nav.Link as={NavLink} to='/homeAdmin/item/'>Item</Nav.Link> */}
                            <Nav.Link as={NavLink} to='/homeAdmin/addItem/'>AddItem</Nav.Link>
                            <Nav.Link as={NavLink} to='/homeAdmin/logout/'>Logout</Nav.Link>
                            
                        </Nav>
                    </Navbar>
                </div>


                <div>
                    <Switch>
                        <Route exact path='/homeAdmin/viewStore/' component={ViewStore}/>
                        <Route exact path='/homeAdmin/viewProduct/' component={Items}/>
                        <Route exact path='/homeAdmin/item/' component={Items}/>
                        <Route exact path='/homeAdmin/addItem/' component={AddItem}/>
                        <Route exact path='/homeAdmin/' component={Stores}/>
                        <Route exact path='/homeAdmin/logout/' component={Logout}/>
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
}

export default HomeAdmin;