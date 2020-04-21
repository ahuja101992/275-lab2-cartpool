import React, {Component} from "react";
import {Button, Col, Card, Form, Modal} from "react-bootstrap";
import {connect} from "react-redux";
import {Redirect} from "react-router";
import {getStoresByAdmin} from "../../redux/actions/inventoryActions";

function mapStateToProps(store) {
    return {
        stores: store.inventory.stores,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        getStoresByAdmin: (payload) => dispatch(getStoresByAdmin(payload)),
    };
}

class Stores extends Component {
    constructor(props) {
        super(props);
        this.state = {
            redirectVar: null,
            selectedOrder: null,
            currentStoreEditIndex: null,
            show: false,
            allOrders: [{_id: 1, name: "foo", street: "1SM", city: "SJ", state: "CA", zip: "95113"},
                {_id: 2, name: "bar", street: "2SM", city: "SF", state: "CA", zip: "98122"}]
        };
    }

    handleClose = () => this.setState({show: false});
    handleShow = (index) => {
        console.log("handleShow index: " + index);
        this.setState({show: true, currentStoreEditIndex: index})
    };

    getStoresByAdmin = (order) => {
        console.log("goToChat")
        console.log("order")
        console.log(order)
        this.setState({redirectVar: true, selectedOrder: order})
    }


    componentDidMount() {
        const payload = {};
        payload.adminId = localStorage.getItem('id');

        this.props.getStoresByAdmin(payload)
    }

    populateSection = () => {
        console.log("populateSection");

        const renderTodos = this.props.stores.map((order, index) => {
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
                            <b>Store Address</b> - {order.address.street + " " + order.address.city + " " + order.address.state + " " + order.address.zip}

                        </Card.Text>
                        <Button onClick={() => this.handleShow(index)} type="button" variant="primary">Edit</Button>
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
                <Button variant="primary" onClick={() => this.handleShow(null)}>
                    Create new store
                </Button>

                <Modal show={this.state.show} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>Modal heading</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>Woohoo, you're reading this text in a modal!</Modal.Body>
                    <Form onSubmit={() => this.saveStoreEdit()}>

                        <Form.Group controlId="name">
                            <Form.Label>Name</Form.Label>
                            <Form.Control defaultValue={this.state.currentStoreEditIndex !== null ? this.props.stores[this.state.currentStoreEditIndex].name : ""}
                                          placeholder="Enter a cool screen name" required />
                        </Form.Group>

                        <Form.Row>
                            <Form.Group as={Col} controlId="city">
                                <Form.Label>City</Form.Label>
                                <Form.Control defaultValue={this.state.currentStoreEditIndex !== null ? this.props.stores[this.state.currentStoreEditIndex].city : ""} placeholder="City name" required />
                            </Form.Group>
                            <Form.Group as={Col} controlId="state">
                                <Form.Label>State</Form.Label>
                                <Form.Control defaultValue={this.state.currentStoreEditIndex !== null ? this.props.stores[this.state.currentStoreEditIndex].state : ""} placeholder="State name or code" required />
                            </Form.Group>
                            <Form.Group as={Col} controlId="zipcode">
                                <Form.Label>Zipcode</Form.Label>
                                <Form.Control defaultValue={this.state.currentStoreEditIndex !== null ? this.props.stores[this.state.currentStoreEditIndex].zip : ""} placeholder="12345 or 12345-6789" required />
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

export default connect(mapStateToProps, mapDispatchToProps)(Stores);