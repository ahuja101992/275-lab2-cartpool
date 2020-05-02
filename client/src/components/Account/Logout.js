import React, {Component} from 'react';
import {Redirect} from 'react-router';

class Logout extends Component {
    render() {
        console.log("Deleted cookie");
        localStorage.removeItem('id');
        localStorage.removeItem('email');

        return (
            <Redirect to="/login"/>
        )
    }
}

export default Logout;