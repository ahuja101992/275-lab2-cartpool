import React, {Component} from "react";
import {connect} from "react-redux";
import {Button, Col, Form, Modal, Toast} from "react-bootstrap";
import BootstrapTable from "react-bootstrap-table-next";
import {pickUpOrder, getOrdersReadyForPickup} from "../../redux/actions/orderActions";
import QRCode from "react-google-qrcode";

function mapStateToProps(store) {
    return {
        ordersReadyForPickup: store.orders.ordersReadyForPickup,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        pickUpOrder: (payload) => dispatch(pickUpOrder(payload)),
        getOrdersReadyForPickup: (payload) => dispatch(getOrdersReadyForPickup(payload)),
    };
}

class Pickup extends Component {
    constructor(props) {
        super(props);
        this.state = {
            redirectVar: null,
            selectedOrder: null,
            currentStoreEditIndex: null,
            showQRCode: false,
            basicColumns: [{
                dataField: 'orderId',
                text: 'Order ID'
            }, {
                dataField: 'customerName',
                text: 'Customer Name'
            }, {
                dataField: 'customerAddress',
                text: 'Customer Address'
            },{
                dataField: 'finalPrice',
                text: 'Final price'
            }, {
                dataField: 'status',
                text: 'status'
            }, {
                dataField: 'action',
                text: 'Action',
                formatter: this.addCheckoutButton.bind(this)
            }],
        };
    }

    componentDidMount() {
        const payload = {};
        payload.poolerId = localStorage.getItem("id");
        payload.storeId = localStorage.getItem("storeId");

        this.props.getOrdersReadyForPickup(payload);
    }

    addCheckoutButton = (cell, row) => {
        return (
            <div>
                <button type="button" className="btn btn-outline-primary btn-sm ts-buttom" size="sm"
                        onClick={this.checkout.bind(cell, row)}>
                    Checkout
                </button>
            </div>
        );
    };

    handleClose = () => this.setState({showQRCode: false});

    checkout = (cell) => {
        console.log("Inside checkout");
        console.log(cell)

        const payload = {};
        payload.deliveryPersonId = localStorage.getItem('id');
        payload.orderId = "";

        this.setState({showQRCode: true})

        this.props.pickUpOrder(payload);
    }

    render() {
        return (
            <div>
                <h1>Pickup</h1>
                <Modal show={this.state.showQRCode} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>QR code</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <QRCode
                        data="1"
                        size={130}
                        framed/>
                    </Modal.Body>

                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.handleClose}>
                            Close
                        </Button>
                    </Modal.Footer>

                </Modal>
                <BootstrapTable keyField='orderId'
                                data={this.props.ordersReadyForPickup}
                                columns={this.state.basicColumns}
                />
            </div>
        );
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Pickup);