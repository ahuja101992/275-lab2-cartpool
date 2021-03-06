import React, {Component} from "react";
import {Badge, Button, Card} from "react-bootstrap";
import {connect} from "react-redux";
import {getOrdersByUserId, markDeliveryNotReceived} from "../../redux/actions/orderActions";
import {
    CANCELED,
    DELIVERED,
    DELIVERED_NOT_RECEIVED,
    PICKED_UP,
    PICKED_UP_BY_SELF,
    PLACED
} from "../../constants/appConstants";

function mapStateToProps(store) {
    return {
        orderByPooler: store.orders.orderByPooler,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        markDeliveryNotReceived: (payload) => dispatch(markDeliveryNotReceived(payload)),
        getOrdersByUserId: (payload) => dispatch(getOrdersByUserId(payload), () => {
            this.forceUpdate()
        }),
    };
}

class Orders extends Component {

    constructor(props) {
        super(props);

        this.state = {
            redirectVar: null,
            selectedOrder: null,
            isLoading: false,
        }
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

            case DELIVERED_NOT_RECEIVED:
                badge = <Badge style={{fontSize: fontSize}} variant="danger">{DELIVERED_NOT_RECEIVED}</Badge>;
                break;

            case CANCELED:
                badge = <Badge style={{fontSize: fontSize}} variant="warning">{CANCELED}</Badge>;
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
        this.props.markDeliveryNotReceived({orderId: order.id, orderOwnerId: localStorage.getItem("id")})
    }

    extractAddress = (address) => {
        return address.street + " " + address.city + " " + address.state + " " + address.zip
    }

    populateSection = () => {
        console.log("populateSection");

        const renderTodos = this.props.orderByPooler.map((order, index) => {
            console.log("order")
            console.log(order)

            return <Card style={{width: '22rem'}} className="col-sm-4 p-0 mx-4 my-4">
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
                        {order.status === DELIVERED &&
                        <Button onClick={() => this.markDeliveryNotReceived(order)} type="button" variant="danger">Mark
                            Delivery Not Received</Button>}
                    </Card.Text>
                </Card.Body>
            </Card>
        });

        return <div>
            <ul className="ul li d-flex flex-row row">{renderTodos}</ul>
        </div>
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