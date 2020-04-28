import React, {Component} from "react";
import {Route} from "react-router-dom";
import NavPage from "./NavPage/NavPage";
import HomePage from "./HomePage";
import SignUp from "./Account/SignUp";
import Login from "./Account/Login";
// import Stores from "./InventoryManagement/Stores";
import AdminLayout from "./Layout/AdminLayout";
import PoolerLayout from "./Layout/PoolerLayout";
import SearchPool from "./SearchPool/SearchPool";

class Main extends Component {
    render() {
        return (
            <div>
                <Route path="/homeAdmin" component={AdminLayout}/>
                <Route path="/homePooler" component={PoolerLayout}/>
                <Route exact path="/" component={NavPage}/>
                <Route exact path="/home" component={HomePage}/>
                <Route exact path="/signup" component={SignUp}/>
                <Route exact path="/login" component={Login}/>
                <Route exact path="/searchPool" component={SearchPool}/>
                {/*<Route exact path="/store" component={Stores} />*/}
            </div>
        );
    }
}

//Export The Main Component
export default Main;
