import React, { Component } from 'react';
import axios from "axios";
import { HOSTNAME } from '../../constants/appConstants';

class Temp extends Component {
    state = {}

    componentDidMount() {
        if (this.props.match.path === "/pool/verify/byPoolLeader/:poolerId?/:poolId?") {
            if (this.props.match.params.poolerId !== undefined && this.props.match.params.poolId !== undefined) {
                let poolerId = this.props.match.params.poolerId;
                let poolId = this.props.match.params.poolId;
                axios.get(`http://${HOSTNAME}:8080/pool/verify/byPoolLeader/${poolerId}/${poolId}`)
                    .then(response =>
                        console.log("in then")
                    )
                    .catch(error => {
                        console.log(error);
                    })
            }

        } else if (this.props.match.path === "/pool/reject/byPoolLeader/:poolerId?/:poolId?") {
            if (this.props.match.params.poolerId !== undefined && this.props.match.params.poolId !== undefined) {
                let poolerId = this.props.match.params.poolerId;
                let poolId = this.props.match.params.poolId;
                axios.get(`http://${HOSTNAME}:8080/pool/reject/byPoolLeader/${poolerId}/${poolId}`)
                    .then(response =>
                        console.log("in then")
                    )
                    .catch(error => {
                        console.log(error);
                    })
            }
        }
        else if (this.props.match.path === "/pool/support/byPooler/:poolerId?/:poolId?") {
            if (this.props.match.params.poolerId !== undefined && this.props.match.params.poolId !== undefined) {
                let poolerId = this.props.match.params.poolerId;
                let poolId = this.props.match.params.poolId;
                axios.get(`http://${HOSTNAME}:8080/pool/support/byPooler/${poolerId}/${poolId}`)
                    .then(response =>
                        console.log("in then")
                    )
                    .catch(error => {
                        console.log(error);
                    })
            }
        }
        else if (this.props.match.path === "/pool/reject/byPooler/:poolerId?/:poolId?") {
            if (this.props.match.params.poolerId !== undefined && this.props.match.params.poolId !== undefined) {
                let poolerId = this.props.match.params.poolerId;
                let poolId = this.props.match.params.poolId;
                axios.get(`http://${HOSTNAME}:8080/pool/reject/byPooler/${poolerId}/${poolId}`)
                    .then(response =>
                        console.log("in then")
                    )
                    .catch(error => {
                        console.log(error);
                    })
            }
        }
    }
    render() {
        window.open("about:blank", "_self");
        window.close();
        return (
            <React.Fragment>

            </React.Fragment>
        );
    }
}

export default Temp;