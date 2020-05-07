import React, {Component} from "react";

class PoolVerificationPage extends Component {
    constructor(props) {
        super(props);

        this.state = {}
    }

    componentDidMount() {
        if (this.props.match.params.email !== undefined) {
            console.log(
                "this.props.match.params.email: " + this.props.match.params.email
            );

            const payload = {};
            payload.email = this.props.match.params.email;
            //axios call
            //in callback, please open up a toast
        } else {
            console.log("undefined");
        }
    }

    render() {
        return (
            <h1>PoolVerificationPage</h1>
        )
    }
}

export default PoolVerificationPage;