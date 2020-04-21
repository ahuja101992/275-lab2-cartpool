import React, {Component} from 'react';
import {Redirect} from 'react-router';

class NavPage extends Component {
    render() {
        return (
            <div>
                {localStorage.getItem("id") === null &&
                <Redirect to={{
                    pathname: "/login"
                }}/>}
                {localStorage.getItem("type") === "admin" ? <Redirect to="/homeAdmin"/> : <Redirect to="/homePooler"/>}

            </div>

        );
    };
}

export default NavPage;