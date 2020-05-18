import React, { Component } from 'react';
import { Redirect } from 'react-router';

class Logout extends Component {
    render() {
        console.log("Deleted cookie");
        localStorage.removeItem('id');
        localStorage.removeItem('email');
        localStorage.removeItem('cart');
        localStorage.removeItem('type');
        localStorage.removeItem('screenname');

        return (
            <Redirect to="/login/" />
        )
    }
}

export default Logout;