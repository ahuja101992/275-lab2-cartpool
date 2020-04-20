import React, {Component} from 'react';
import {Switch} from 'react-router';
import {Route} from "react-router-dom";
import HomePooler from '../Home/HomePooler';

class PoolerLayout extends Component {
    render() {
        return (
            <div>
                <Switch>
                    <Route path="/homePooler" component={HomePooler}/>
                </Switch>
            </div>
        );
    }
}

export default PoolerLayout;