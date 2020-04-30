import React, { Component } from "react";
import { connect } from "react-redux";
import BootstrapTable from "react-bootstrap-table-next";
import { Button, Modal, Dropdown, Form, Col } from "react-bootstrap";
import { HOSTNAME } from "../../constants/appConstants";
import axios from "axios";
import "./checkout.css";

class Checkout extends Component {
  constructor(props) {
    super(props);
    this.state = {
      price: 20,
      finalPrice: null,
      deliverySelection: false,
      forDelivery: true,
      pickupOrders: null,
      showPickUpModel: false,
      show: false,
      noOfOrders: 0,
      warning: false,
      orderList: [],
      pickUpSelectionSuccess: false,
    };
  }

  componentDidMount() {}
  handleDeliverySelection = () => {
    this.setState({
      deliverySelection: true,
      forDelivery: true,
    });
  };
  hendlePickUpSelection = () => {
    // let poolerId = localStorage.getItem("poolerId");
    // let storeId = localStorage.getItem("storeId");
    let storeId = 1;
    let poolerId = 1;
    axios.defaults.withCredential = true;
    axios
      .get(
        `http://${HOSTNAME}:8080/order/getOrdersForPickup/${poolerId}/${storeId}`
      )
      .then((response) => {
        this.setState({
          pickupOrders: response.data,
          show: true,
        });
      })
      .catch((error) => {
        console.log(error);
      });
  };
  handleClose = () => this.setState({ show: false });
  handleShow = () => this.setState({ show: true });
  handleSelectPickUpOrders = () => {
    console.log("jhjhjh");
    this.setState({ show: false, deliverySelection: true });
    console.log("yayduydsuyfyusdyu");
    let list = [];

    if (this.state.noOfOrders > 0) {
      if (this.state.noOfOrders > this.state.pickupOrders.length)
        this.setState({ show: false, warning: true });
      else {
        for (let i = 0; i < this.state.noOfOrders; i++) {
          list.push(this.state.pickupOrders[i].id);
        }
        this.setState({ orderList: list });
      }
    }
    ///////////////////////////////THIS HHAS TO BE MOVED TO PLACE ORDER BY SAKSHI
    // let poolerId = localStorage.getItem("poolerId");
    // let storeId = localStorage.getItem("storeId");
    let storeId = 1;
    let poolerId = 1;
    const requestParams = {};
    requestParams.poolerId = poolerId;
    requestParams.count = storeId;
    axios.defaults.withCredential = true;
    axios
      .post(
        `http://${HOSTNAME}:8080/order/selectorders`,
        this.state.orderList,
        {
          params: requestParams,
        }
      )
      .then((response) => {
        this.setState({
          pickUpSelectionSuccess: true,
        });
      })
      .catch((error) => {
        console.log(error);
      });
  };
  handleWarningClose = () => {
    this.setState({ warning: false });
  };
  getPlaceOrderBtnClass = () => {
    let classes = "btn place-order-btn btn-";
    classes += this.state.deliverySelection ? "success" : "secondary disabled";
    return classes;
  };
  getButtonState = () => {
    return this.state.deliverySelection ? "" : "disabled";
  };
  getOrdersForPickUp = () => {
    if (this.state.pickupOrders === "" || this.state.pickupOrders === null)
      return <div>Yuppie ........ No orders for pickup</div>;
    else {
      return this.state.pickupOrders.map((order) => {
        return (
          <div className="list-group" key={order.id}>
            <div
              className="list-group-item list-group-item-action order-list row"
              id={"orderList-" + order.id}
            >
              <div className="order-row row">
                <div className="quantity-col col-sm-4">
                  Order Id : {order.id}
                </div>
                <div className="quantity-col col-sm-4">
                  Quantity : {order.qty}
                </div>
                <div className="price-col col-sm-4">
                  Total Price : {order.price}
                </div>
              </div>
            </div>
          </div>
        );
      });
    }
  };
  render() {
    if (this.props.location.props && this.props.location.props.price !== "") {
      this.setState({
        price: this.props.location.props.price,
        finalPrice: this.props.location.props.price * 1.0975,
      });
    }
    return (
      <div>
        <h4>Order Details for Checkout</h4>
        <Modal show={this.state.show} onHide={this.handleClose}>
          <Modal.Header closeButton>
            <Modal.Title>Select orders for pickup</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <div className="dropdown row">
              <div className="dropdown-label col-sm-6">
                Select Number of Orders
              </div>
              <div className="dropdown-button col-sm-4">
                <Form.Group controlId="formGridState">
                  <Form.Control
                    as="select"
                    value={this.state.noOfOrders}
                    onChange={(e) =>
                      this.setState({ noOfOrders: e.target.value })
                    }
                  >
                    {[...Array(11)].map((curr, i) => (
                      <option value={i} key={i}>
                        {i}
                      </option>
                    ))}
                  </Form.Control>
                </Form.Group>
              </div>
            </div>
            {this.getOrdersForPickUp()}
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={this.handleClose}>
              Close
            </Button>
            <Button variant="primary" onClick={this.handleSelectPickUpOrders}>
              Confirm
            </Button>
          </Modal.Footer>
        </Modal>
        <Modal show={this.state.warning} animation={false}>
          <Modal.Body>
            Number of orders selected for pickup is greater than prders present.
            So no orders will be assigned fo you to pick up.{" "}
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={this.handleWarningClose}>
              Close
            </Button>
          </Modal.Footer>
        </Modal>
        <div class="order-checkout-container">
          <div class="order-total row">
            <div class="col-sm-6 order-total-title">Order Total</div>
            <div class="col-sm-6 order-total">$ {this.state.price}</div>
          </div>
          <div class="order-service-tax row">
            <div class="col-sm-6 service-tax">Universal Tax</div>
            <div class="col-sm-6 service-tax">
              $ {this.state.price * 0.0925}
            </div>
          </div>
          <div class="order-convenience-fee row">
            <div class="col-sm-6 convenience-fee">Convenience-Fee</div>
            <div class="col-sm-6 convenience-fee">
              $ {this.state.price * 0.005}
            </div>
          </div>
          <div class="order-final-price row">
            <div class="col-sm-6 order-total-title">Amount Payable</div>
            <div class="col-sm-6 order-total">
              $ {this.state.price * 1.0975}
            </div>
          </div>
          <div class="delivery-selection-header">
            <h5>Choose your delivery option</h5>
          </div>
          <div class="button-container row">
            <div class="col-sm-4 pickup-btn">
              <button
                type="button"
                class="btn btn-primary selection-btn "
                onClick={this.hendlePickUpSelection}
              >
                Pickup
              </button>
            </div>
            <div class="col-sm-4 delivery-btn">
              <button
                type="button"
                class="btn btn-warning selection-btn"
                onClick={this.handleDeliverySelection}
              >
                Delivery
              </button>
            </div>
          </div>
          <div class="place-order row">
            <div class="col-sm-7 ">
              <button
                disabled={!this.state.deliverySelection}
                type="button"
                class={this.getPlaceOrderBtnClass()}
              >
                Place Order
              </button>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Checkout;
