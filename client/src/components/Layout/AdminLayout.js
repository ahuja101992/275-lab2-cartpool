import React, {Component} from 'react';
import {Switch} from 'react-router';
import {Route} from "react-router-dom";
import HomeAdmin from '../Home/HomeAdmin';

class AdminLayout extends Component {
    render() {
        return (
            <div>
                <Switch>
                    <Route path="/homeAdmin" component={HomeAdmin}/>
                </Switch>
            </div>
        );
    }
}

export default AdminLayout;