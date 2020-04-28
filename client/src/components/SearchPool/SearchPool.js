import React, { Component } from 'react';
import { HOSTNAME } from "../../constants/appConstants";
import axios from 'axios';

class SearchPool extends Component {
    constructor(props) {
        super(props);
        this.state = {
            searchParam: "",
            pools: []
        };
    }

    submitHandler = () => {
        let searchParam = this.state.searchParam;
        axios.defaults.withCredential = true;
        axios.get(`http://${HOSTNAME}:8080/pool/search/${searchParam}`)
            .then(response => {
                console.log(response);
            })
            .catch(error => {
                console.log(error);
            })
    };

    changeHandeler = (e) => {
        this.setState({
            searchParam: e.target.value
        });
    };

    render() {
        return (
            <React.Fragment>
                <div><input type="text" className="search-box" value={this.state.searchParam} onChange={this.changeHandeler}
                    placeholder="Pizza, sushi, ...." /></div>

            </React.Fragment>
        );
    }
}

export default SearchPool;