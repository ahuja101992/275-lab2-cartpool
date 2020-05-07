import React, { Component } from 'react';


class Temp extends Component {
    state = {}

    componentDidMount() {
        console.log(this.props)
        if (this.props.match.params.obj1 !== undefined) {
            console.log(
                "this.props.match.params.obj1: " + this.props.match.params.obj1
            );

        } else {
            console.log("undefined");
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