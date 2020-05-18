import React, { Component } from "react";
import { Button, Card, Col, Form, Modal } from "react-bootstrap";
import { connect } from "react-redux";
import { createStore, deleteStore, getStoresByAdmin } from "../../redux/actions/inventoryActions";

function mapStateToProps(store) {
    return {
        stores: store.inventory.stores,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        getStoresByAdmin: (payload) => dispatch(getStoresByAdmin(payload)),
        createStore: (payload) => dispatch(createStore(payload)),
        deleteStore: (payload) => dispatch(deleteStore(payload)),
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
            allOrders: [{ _id: 1, name: "foo", street: "1SM", city: "SJ", state: "CA", zip: "95113" },
            { _id: 2, name: "bar", street: "2SM", city: "SF", state: "CA", zip: "98122" }]
        };
    }

    handleClose = () => this.setState({ show: false });
    handleShow = (index) => {
        console.log("handleShow index: " + index);
        this.setState({ show: true, currentStoreEditIndex: index })
    };

    getStoresByAdmin = (order) => {
        console.log("getStoresByAdmin")
        console.log(order)
        this.setState({ redirectVar: true, selectedOrder: order })
    }

    deleteStore = (store) => {
        const payload = {};
        payload.storeId = store.id;
        payload.adminId = localStorage.getItem('id');

        this.props.deleteStore(payload)
        this.setState({ currentStoreEditIndex: null });
    }

    createStore = (e) => {
        e.preventDefault();

        console.log("Inside createStore")

        const data = {};
        for (let i = 0; i < e.target.length; i++) {
            if (e.target[i].id !== "") {
                data[e.target[i].id] = e.target[i].value;
            }
        }

        let updatedData = {
            name: data.name,
            street: data.street,
            city: data.city,
            state: data.state,
            zip: data.zip,
            storeId: this.state.currentStoreEditIndex !== null ? this.props.stores[this.state.currentStoreEditIndex].id : null,
            adminId: localStorage.getItem('id'),
        }

        this.props.createStore(updatedData);
        this.handleClose();
    }

    componentDidMount() {
        const payload = {};
        payload.adminId = localStorage.getItem('id');

        this.props.getStoresByAdmin(payload)
    }

    populateSection = () => {
        console.log("populateSection");

        const renderTodos = this.props.stores.map((store, index) => {
            return <Card style={{ width: '22rem' }} className="col-sm-4 p-0 mx-4 my-4">
                {/*<Card.Img variant="top" src={require("../../images/restaurant-logo.png")}/>*/}
                <Card.Body>
                    <Card.Title><h3>{store.name}</h3></Card.Title>
                    <Card.Text>
                        <b>Store Name</b> - {store.name}
                        <br />
                        <b>Store
                                Address</b> - {store.address.street + " " + store.address.city + " " + store.address.state + " " + store.address.zip}

                    </Card.Text>
                    <div className="d-flex justify-content-around"><Button onClick={() => this.handleShow(index)} type="button" variant="primary">Edit</Button>
                        <Button onClick={() => this.deleteStore(store)} type="button" variant="primary">Delete</Button></div>

                </Card.Body>
            </Card>

        });

        return <div>
            <ul className="ul li d-flex flex-row row">{renderTodos}</ul>
        </div>;
    }

    render() {
        return (
            <div style={styles.container}>
                <Button variant="primary" style={styles.button} onClick={() => this.handleShow(null)}>
                    Create new store
                </Button>

                <Modal show={this.state.show} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>Edit store</Modal.Title>
                    </Modal.Header>

                    <Form onSubmit={this.createStore}>
                        <Form.Group controlId="name" style={styles.formField}>
                            <Form.Label>Name</Form.Label>
                            <Form.Control
                                defaultValue={this.state.currentStoreEditIndex !== null ? this.props.stores[this.state.currentStoreEditIndex].name : ""}
                                placeholder="Enter store name" required />
                        </Form.Group>

                        <Form.Group controlId="street" style={styles.formField}>
                            <Form.Label>Street</Form.Label>
                            <Form.Control
                                defaultValue={this.state.currentStoreEditIndex !== null ? this.props.stores[this.state.currentStoreEditIndex].address.street : ""}
                                placeholder="Enter store street" required />
                        </Form.Group>

                        <Form.Row>
                            <Form.Group as={Col} controlId="city" style={styles.formField}>
                                <Form.Label>City</Form.Label>
                                <Form.Control
                                    defaultValue={this.state.currentStoreEditIndex !== null ? this.props.stores[this.state.currentStoreEditIndex].address.city : ""}
                                    placeholder="Enter store city" required />
                            </Form.Group>
                            <Form.Group as={Col} controlId="state">
                                <Form.Label>State</Form.Label>
                                <Form.Control
                                    defaultValue={this.state.currentStoreEditIndex !== null ? this.props.stores[this.state.currentStoreEditIndex].address.state : ""}
                                    placeholder="Enter store state" required />
                            </Form.Group>
                            <Form.Group as={Col} controlId="zip" style={styles.formField}>
                                <Form.Label>Zipcode</Form.Label>
                                <Form.Control
                                    defaultValue={this.state.currentStoreEditIndex !== null ? this.props.stores[this.state.currentStoreEditIndex].address.zip : ""}
                                    placeholder="Enter store zipcode" required />
                            </Form.Group>
                        </Form.Row>

                        <Modal.Footer>
                            <Button variant="secondary" onClick={this.handleClose}>
                                Close
                            </Button>
                            <Button variant="primary" type="submit">
                                Save changes
                            </Button>
                        </Modal.Footer>
                    </Form>

                </Modal>

                <div style={styles.storeList}>
                    {this.populateSection()}
                </div>
            </div>
        );
    }
}

const styles = {
    container: {
        flex: 1,
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'stretch',
    },
    button: {
        marginLeft: "45%"
    },
    storeList: {
        flex: 2,
        alignSelf: 'left'
    },
    formField: {
        marginLeft: "1rem",
        marginRight: "1rem"
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Stores);