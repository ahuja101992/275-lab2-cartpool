import React, {Component} from "react";
import {Badge, Button, Card} from "react-bootstrap";
import {connect} from "react-redux";
import {getOrdersByUserId, markDeliveryNotReceived} from "../../redux/actions/orderActions";
import {DELIVERED, PICKED_UP, PICKED_UP_BY_SELF, PLACED} from "../../constants/appConstants";

function mapStateToProps(store) {
    return {
        orderByPooler: store.orders.orderByPooler,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        markDeliveryNotReceived: (payload) => dispatch(markDeliveryNotReceived(payload)),
        getOrdersByUserId: (payload) => dispatch(getOrdersByUserId(payload)),

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
            case PLACED:
                badge = <Badge style={{fontSize: fontSize}} variant="primary">{PLACED}</Badge>;
                break;

            case PICKED_UP_BY_SELF:
                badge = <Badge style={{fontSize: fontSize}} variant="info">{PICKED_UP_BY_SELF}</Badge>;
                break;

            case PICKED_UP:
                badge = <Badge style={{fontSize: fontSize}} variant="dark">{PICKED_UP}</Badge>;
                break;

            case DELIVERED:
                badge = <Badge style={{fontSize: fontSize}} variant="success">{DELIVERED}</Badge>;
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
        payload.userId = localStorage.getItem('id');

        this.props.getOrdersByUserId(payload);
    }

    markDeliveryNotReceived = (order) => {
        console.log("markDeliveryNotReceived")
        console.log(order)
        this.props.markDeliveryNotReceived({orderId: order.id})
    }

    extractAddress = (address) => {
        return address.street + " " + address.city + " " + address.state + " " + address.zip
    }

    populateSection = () => {
        console.log("populateSection");

        const renderTodos = this.props.orderByPooler.map((order, index) => {
            console.log("order")
            console.log(order)

            return <ul key={index}>
                <Card style={{width: '22rem'}}>
                    <Card.Img variant="top" src={require("../../images/restaurant-logo.png")}/>
                    <Card.Body>
                        <Card.Title><b>Store</b> - {order.store.name}</Card.Title>
                        <Card.Text>
                            <b>Order Id</b> - {order.id}
                            <br/>
                            <b>Customer Address</b> - {this.extractAddress(order.orderOwner.address)}
                            <br/>
                            <b>Price</b> - ${order.finalPrice}
                            <br/>
                            <b>Order Status</b> - {this.getOrderStatusBadge(order.status)}
                            <br/><br/>
                            {order.status === "Delivered" &&
                            <Button onClick={() => this.markDeliveryNotReceived(order)} type="button" variant="danger">Mark
                                Delivery Not Received</Button>}
                        </Card.Text>
                    </Card.Body>
                </Card>
            </ul>;
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