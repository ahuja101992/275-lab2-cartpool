import React, {Component} from "react";
import {connect} from "react-redux";
import {Badge, Button, Card, Col, Form, ListGroup, Modal} from "react-bootstrap";
import BootstrapTable from "react-bootstrap-table-next";
import {getOrdersReadyForDelivery, markDelivered} from "../../redux/actions/orderActions";
import {DELIVERED} from "../../constants/appConstants";

function mapStateToProps(store) {
    return {
        ordersReadyForDelivery: store.orders.ordersReadyForDelivery,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        getOrdersReadyForDelivery: (payload) => dispatch(getOrdersReadyForDelivery(payload)),
        markDelivered: (payload) => dispatch(markDelivered(payload)),

    };
}

class Messaging extends Component {
    constructor(props) {
        super(props);
        this.state = {
            redirectVar: null,
            selectedOrder: null,
            currentStoreEditIndex: null,
            show: false,
            poolers: ['ram', 'shyam', 'sita'],
            currentPooler: null,
            showEmailBox: false
        };
    }

    populateOrdersReadyForDelivery = () => {
        const payload = {};
        payload.poolerId = localStorage.getItem("id");

        this.props.getOrdersReadyForDelivery(payload);
    }

    componentDidMount() {
        this.populateOrdersReadyForDelivery();
    }

    userClicked = (pooler) => {
        console.log("In alertClicked: " + pooler)
        this.setState({showEmailBox: true, currentPooler: pooler})
    }

    handleClose = () => this.setState({showEmailBox: false});

    render() {
        const renderTodos = this.state.poolers.map((pooler, index) => {
            console.log("pooler")
            console.log(pooler)

            return <ListGroup.Item style={{margin: "1px", width: "10rem"}} action onClick={() => this.userClicked(pooler)}>
                {pooler}
            </ListGroup.Item>
        });

        return (
            <div>
                <h1>Messaging</h1>
                <div className='rowC'>
                    <ListGroup>
                        {renderTodos}
                    </ListGroup>

                    {this.state.showEmailBox && <Form style={styles.emailBox} onSubmit={this.createStore}>
                        <Form.Group controlId="name" style={styles.formField}>
                            <Form.Label>Name</Form.Label>
                            <Form.Control
                                disabled={true}
                                defaultValue={this.state.currentPooler !== null ? this.state.currentPooler : ""}/>
                        </Form.Group>

                        <Form.Group controlId="street" style={styles.formField}>
                            <Form.Label>Street</Form.Label>
                            <Form.Control
                                as="textarea" rows="3"
                                placeholder="Enter store street" required/>
                        </Form.Group>

                        <Modal.Footer>
                            <Button variant="secondary" onClick={this.handleClose}>
                                Cancel
                            </Button>
                            <Button variant="primary" type="submit">
                                Send
                            </Button>
                        </Modal.Footer>
                    </Form>}
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
    emailBox: {
      marginLeft: "5rem",
        width: "30%"
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

export default connect(mapStateToProps, mapDispatchToProps)(Messaging);