import React, {Component} from "react";
import {Button, Card, Col, Form, Modal} from "react-bootstrap";
import {connect} from "react-redux";
import {createStore, deleteStore, getStoresByAdmin} from "../../redux/actions/inventoryActions";
import BootstrapTable from "react-bootstrap-table-next";
import {checkout} from "../../redux/actions/orderActions";


function mapStateToProps(store) {
    return {
        stores: store.inventory.stores,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        checkout: (payload) => dispatch(checkout(payload)),
    };
}

class Pickup extends Component {
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

            orders: [{"orderId": "1", "customerName": "xyz", "customerAddress": "1SM", "status": "OK"}]
        };
    }

    componentDidMount() {
        const payload = {};
        payload.adminId = localStorage.getItem('id');
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

    checkout(cell) {
        console.log("Inside makeOrderStatusPreparing");
        this.props.checkout({_id: cell.orderId, newStatus: "Preparing", owner_id: cell.owner_id});

    }

    render() {
        return (
            <div>
                <h1>Pickup</h1>
                <BootstrapTable keyField='orderId'
                                data={this.state.orders}
                                columns={this.state.basicColumns}
                />
            </div>
        );
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Pickup);