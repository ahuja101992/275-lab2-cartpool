import React, {Component} from "react";
import {Button, Card, Col, Form, Modal} from "react-bootstrap";
import {connect} from "react-redux";
import BootstrapTable from "react-bootstrap-table-next";
import {checkout} from "../../redux/actions/orderActions";

function mapStateToProps(store) {
    return {
        ordersReadyForCheckout: store.orders.ordersReadyForCheckout,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        checkoutOrder: (payload) => dispatch(checkout(payload)),
    };
}

class Delivery extends Component {
    constructor(props) {
        super(props);
        this.state = {
            redirectVar: null,
            selectedOrder: null,
            currentStoreEditIndex: null,
            show: false,
            basicColumns: [{
                dataField: 'orderId',
                text: 'Order ID'
            }, {
                dataField: 'customerName',
                text: 'Customer Name'
            }, {
                dataField: 'customerAddress',
                text: 'Customer Address'
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
    }

    addCheckoutButton = (cell, row) => {
        return (
            <div>
                <button type="button" className="btn btn-outline-primary btn-sm ts-buttom" size="sm"
                        onClick={this.checkout.bind(cell, row)}>
                    Mark delivered
                </button>
            </div>
        );
    };

    checkout = (cell) => {
        console.log("Inside checkout");
        console.log(cell)

        const payload = {};
        payload.deliveryPersonId = localStorage.getItem('id');
        payload.orderId = "";

        this.props.checkoutOrder(payload);
    }

    render() {
        return (
            <div>
                <h1>Delivery</h1>
                <BootstrapTable keyField='orderId'
                                data={this.props.ordersReadyForCheckout}
                                columns={this.state.basicColumns}
                />
            </div>
        );
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Delivery);