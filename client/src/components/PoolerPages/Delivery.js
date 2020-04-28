import React, {Component} from "react";
import {connect} from "react-redux";
import BootstrapTable from "react-bootstrap-table-next";
import {getOrdersReadyForDelivery, markDelivered} from "../../redux/actions/orderActions";

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
                formatter: this.addMarkDeliveredButton.bind(this)
            }],
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

    addMarkDeliveredButton = (cell, row) => {
        return (
            <div>
                <button type="button" className="btn btn-outline-primary btn-sm ts-buttom" size="sm"
                        onClick={this.markDelivered.bind(cell, row)}>
                    Mark delivered
                </button>
            </div>
        );
    };

    markDelivered = (cell) => {
        console.log("Inside checkout");
        console.log(cell)

        const payload = {};
        payload.orderId = cell.orderId;

        this.props.markDelivered(payload, () => this.populateOrdersReadyForDelivery());
    }

    render() {
        return (
            <div>
                <h1>Delivery</h1>
                <BootstrapTable keyField='orderId'
                                data={this.props.ordersReadyForDelivery}
                                columns={this.state.basicColumns}
                />
            </div>
        );
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Delivery);