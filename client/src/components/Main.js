import React, { Component } from "react";
import { Route } from "react-router-dom";
import NavPage from "./NavPage/NavPage";
import HomePage from "./HomePage";
import SignUp from "./Account/SignUp";
import Login from "./Account/Login";

class Main extends Component {
    render() {
        return (
            <div>
                <Route exact path="/" component={NavPage} />
                <Route exact path="/home" component={HomePage} />
                <Route exact path="/signup" component={SignUp} />
                <Route exact path="/login" component={Login}/>
            </div>
        );
    }
}

//Export The Main Component
export default Main;
