import React, { Component } from 'react';
import '../css/list.css'

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
                Homepage
            </div>
        );
    }
}

export default HomePage;

