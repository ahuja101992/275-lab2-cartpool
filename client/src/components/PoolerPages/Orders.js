import React, {Component} from "react";
import {Button, Card, Badge} from "react-bootstrap";
import {connect} from "react-redux";
import {Redirect} from "react-router";
import {markDeliveryNotReceived} from "../../redux/actions/orderActions";

function mapStateToProps(store) {
    return {
        orderByPooler: store.orders.orderByPooler,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        markDeliveryNotReceived: (payload) => dispatch(markDeliveryNotReceived(payload))
    };
}

class Orders extends Component {
    constructor(props) {
        super(props);
        this.state = {
            redirectVar: null,
            selectedOrder: null,
        };
    }

    getOrderStatusBadge = (status) => {
        const fontSize = 20;
        let badge = null;


        switch (status) {
            case "Placed":
                badge = <Badge style={{fontSize: fontSize}} variant="primary">Placed</Badge>;
                break;

            case "Picked-up by oneself":
                badge = <Badge style={{fontSize: fontSize}} variant="info">Picked-up by oneself</Badge>;
                break;

            case "Ready":
                badge = <Badge style={{fontSize: fontSize}} variant="dark">x</Badge>;
                break;

            case "Delivered":
                badge = <Badge style={{fontSize: fontSize}} variant="success">Delivered</Badge>;
                break;

            case "Cancel":
                badge = <Badge style={{fontSize: fontSize}} variant="danger">x</Badge>;
                break;

            case "Warning":
                badge = <Badge style={{fontSize: fontSize}} variant="warning">x</Badge>;
                break;
        }

        return badge;
    };


    componentDidMount() {
        const payload = {};
        payload.userId = localStorage.getItem('_id');
        payload.statusCode = "All";

        //this.props.getOrdersByStatus(payload);
    }

    markDeliveryNotReceived = (order) => {
        this.props.markDeliveryNotReceived({orderId: order.orderId})
    }

    populateSection = () => {
        console.log("populateSection");

        const renderTodos = this.props.orderByPooler.map((order, index) => {
            console.log("order")
            console.log(order)

            return <lu key={index}>
                <Card style={{width: '22rem'}}>
                    <Card.Img variant="top" src={require("../../images/restaurant-logo.png")}/>
                    <Card.Body>
                        <Card.Title><b>Order Id</b> - {order.orderId}</Card.Title>
                        <Card.Text>
                            <b>Customer Address</b> - {order.customerAddress}
                            <br/>
                            <b>Order Status</b> - {this.getOrderStatusBadge(order.status)}
                            <br/><br/>
                            {order.status === "Delivered" && <Button onClick={() => this.markDeliveryNotReceived(order)} type="button" variant="danger">Mark Delivery Not Received</Button>}
                        </Card.Text>
                    </Card.Body>
                </Card>
            </lu>;
        });

        return <div>
            <ul className="ul li">{renderTodos}</ul>
        </div>;

    }

    render() {

        return (
            <div>
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

export default connect(mapStateToProps, mapDispatchToProps)(Orders);