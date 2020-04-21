import React, {Component} from 'react';
import {Nav, Navbar} from "react-bootstrap";
import {NavLink} from "react-router-dom";
import {Redirect} from "react-router";

class HomePage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            currentScreen: "Home",
            viewDetailedTweetScreenPropId: null,
            searchText: null,
            viewDetailedListProps: null
        }
    }

    render() {
        console.log(this.state);
        return (
            <div>
                {/*<div styles={{width:"170px", height:"170px"}}>*/}
                {/*    <img styles={{width:"170px", height:"170px"}} src={logo}/>*/}
                {/*</div>*/}
                {localStorage.getItem("id") === null &&
                <Redirect to={{
                    pathname: "/login"
                }}/>}
                <div>
                    <Navbar>
                        <Nav>
                            <Nav.Link as={NavLink} to='/homeOwner/orders/'>Orders</Nav.Link>
                            <Nav.Link as={NavLink} to='/homeOwner/menu/'>Menu</Nav.Link>

                        </Nav>
                        <Nav className="ml-auto">
                            <Nav.Link as={NavLink} to='/homeOwner/help'>Help</Nav.Link>
                            <Nav.Link as={NavLink} to='/homeOwner/chat'>Chat</Nav.Link>
                            <Nav.Link as={NavLink} to='/homeOwner/profileOwner/'>Profile</Nav.Link>
                            <Nav.Link as={NavLink} to='/homeOwner/signOut/'>SignOut</Nav.Link>
                        </Nav>
                    </Navbar>
                </div>
            </div>
        );
    }
}

export default HomePage;