import React, { Component } from "react";
import { Route } from "react-router-dom";
import NavPage from "./NavPage/NavPage";
import SignUp from "./Account/SignUp";
import Login from "./Account/Login";
import Logout from "./Account/Logout";
import PoolVerificationPage from "./PoolerPages/PoolVerificationPage";

import AdminLayout from "./Layout/AdminLayout";
import PoolerLayout from "./Layout/PoolerLayout";
import SearchPool from "./SearchPool/SearchPool";
// import PoolInfo from "./PoolInfo/PoolInfo";
import NewProfile from "./NewProfilePage/NewProfile"
import ProfilePage from "./ProfilePage/ProfilePage";

class Main extends Component {
    render() {
        return (
            <div>

                <Route path="/homeAdmin" component={AdminLayout} />
                <Route path="/homePooler" component={PoolerLayout} />
                <Route exact path="/" component={NavPage} />
                <Route exact path="/signup" component={SignUp} />
                <Route exact path="/login/:email?" component={Login} />
                <Route exact path="/PoolVerificationPage/:email?" component={PoolVerificationPage} />
                <Route exact path="/logout" component={Logout} />
                <Route exact path="/searchPool" component={SearchPool} />
                {/* <Route exact path="/PoolInfo" component={PoolInfo} /> */}
                <Route exact path="/ProfilePage" component={ProfilePage} />
                <Route exact path="/NewProfile" component={NewProfile} />
                {/*<Route exact path="/store" component={Stores} />*/}
            </div>
        );
    }
}

//Export The Main Component
export default Main;
