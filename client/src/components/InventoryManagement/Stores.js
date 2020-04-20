import React, {Component} from "react";
import {Button, Col, Card, Form, Modal} from "react-bootstrap";
import {connect} from "react-redux";
import {Redirect} from "react-router";

function mapStateToProps(store) {
    return {
        //allOrders: store.restaurant.allOrders,
    }
}

function mapDispatchToProps(dispatch) {
    return {
    };
}

class HelpPage extends Component {
    constructor(props) {
        super(props);
        this.state = {
            redirectVar: null,
            selectedOrder: null,
            show: false,
            allOrders: [{_id: 1, name: "foo", street: "1SM", city: "SJ", state: "CA", zip: "95113"},
                {_id: 2, name: "bar", street: "2SM", city: "SF", state: "CA", zip: "98122"}]
        };

        this.goToChat = this.goToChat.bind(this);
    }

    handleClose = () => this.setState({show: false});
    handleShow = () => this.setState({show: true});

    goToChat = (order) => {
        console.log("goToChat")
        console.log("order")
        console.log(order)
        this.setState({redirectVar: true, selectedOrder: order})
    }

    saveStoreEdit = () => {
        console.log("saveStoreEdit")
    }

    componentDidMount() {
        const payload = {};
        payload.userId = localStorage.getItem('_id');
        payload.statusCode = "All";

    }

    populateSection = () => {
        console.log("populateSection");

        const renderTodos = this.state.allOrders.map((order, index) => {
            // const items = JSON.parse(order.items);
            console.log("order")
            console.log(order)

            return <li key={index}>
                <Card style={{width: '22rem'}}>
                    {/*<Card.Img variant="top" src={require("../../images/restaurant-logo.png")}/>*/}
                    <Card.Body>
                        <Card.Title>Store</Card.Title>
                        <Card.Text>
                            <b>Store Name</b> - {order.name}
                            <br/>
                            <b>Store Address</b> - {order.street + " " + order.city + " " + order.state + " " + order.zip}

                        </Card.Text>
                        <Button onClick={() => this.handleShow()} type="button" variant="primary">Edit</Button>
                        <br/>
                        <br/>
                        <Button onClick={() => this.goToChat(order)} type="button" variant="primary">Delete</Button>
                    </Card.Body>
                </Card>
            </li>;
        });

        return <div>
            <ul className="ul li">{renderTodos}</ul>
        </div>;

    }

    render() {
        return (
            <div>
                {this.state.redirectVar !== null && <Redirect to={{
                    pathname: "/homeBuyer/chat",
                    state: {selectedOrder: this.state.selectedOrder}
                }}/>}

                <Modal show={this.state.show} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>Modal heading</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>Woohoo, you're reading this text in a modal!</Modal.Body>
                    <Form onSubmit={() => this.saveStoreEdit()}>

                        <Form.Group controlId="name">
                            <Form.Label>Name</Form.Label>
                            <Form.Control defaultValue="v" placeholder="Enter a cool screen name" required />
                        </Form.Group>

                        <Form.Group controlId="nickName" >
                            <Form.Label>Nickname</Form.Label>
                            <Form.Control placeholder="Enter a cool nickname" required />
                        </Form.Group>

                        <Form.Row>
                            <Form.Group as={Col} controlId="city">
                                <Form.Label>City</Form.Label>
                                <Form.Control placeholder="City name" required />
                            </Form.Group>
                            <Form.Group as={Col} controlId="state">
                                <Form.Label>State</Form.Label>
                                <Form.Control placeholder="State name or code" required />
                            </Form.Group>
                            <Form.Group as={Col} controlId="zipcode">
                                <Form.Label>Zipcode</Form.Label>
                                <Form.Control placeholder="12345 or 12345-6789" required />
                            </Form.Group>
                        </Form.Row>
                    </Form>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.handleClose}>
                            Close
                        </Button>
                        <Button variant="primary" onClick={this.saveStoreEdit}>
                            Save Changes
                        </Button>
                    </Modal.Footer>
                </Modal>
                {this.populateSection()}
            </div>
        );
    }
}

const styles = {
    container: {
        flex: 1,
        display: "flex",
        flexDirection: "row",
        height: "100vh",
    },
    channelList: {
        display: "flex",
        flex: 1,
        flexDirection: "column",
    },
    chat: {
        display: "flex",
        flex: 3,
        flexDirection: "column",
        borderWidth: "1px",
        borderColor: "#ccc",
        borderRightStyle: "solid",
        borderLeftStyle: "solid",
    },
    settings: {
        display: "flex",
        flex: 1,
        flexDirection: "column",
    },
};

export default connect(mapStateToProps, mapDispatchToProps)(HelpPage);