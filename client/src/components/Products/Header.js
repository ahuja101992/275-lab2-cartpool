import React, { Component } from "react";
import CartScrollBar from "./CartScrollBar";
import Counter from "./Counter";
import EmptyCart from "./EmptyCart";
import CSSTransitionGroup from "react-transition-group/CSSTransitionGroup";
import { findDOMNode } from "react-dom";
import { Link } from "react-router-dom";
import AddItem from "../AdminPages/AddItem";
import "./css/item.css";

class Header extends Component {
  constructor(props) {
    super(props);
    this.myRef = React.createRef();
    this.state = {
      showCart: false,
      cart: this.props.cartItems,
      mobileSearch: false,
      modal: false,
      value: true,
    };
    this.increment = this.increment.bind(this);
    this.decrement = this.decrement.bind(this);
    this.handleUpdate = this.handleUpdate.bind(this);
    this.cancelEdit = this.cancelEdit.bind(this);
  }

  increment(product) {
    this.setState({
      value: true,
    });
    console.log("product increment", product.qty);
    this.props.addOne(product);
  }

  cancelEdit() {
    this.setState({
      modal: false,
    });
  }

  decrement(product) {
    if (product.qty > 0) {
      console.log("product delete ", product);
      this.props.deleteOne(product);
    } else {
      this.setState({
        value: false,
      });
    }
  }

  handleCart(e) {
    e.preventDefault();
    this.setState({
      showCart: !this.state.showCart,
    });
  }

  handleSubmit(e) {
    const searchStoreId = findDOMNode(this.refs.searchStoreId);
    console.log("dom::", searchStoreId);
    e.preventDefault();
  }

  handleUpdate(e) {
    this.setState({
      modal: true,
    });
  }

  handleMobileSearch(e) {
    e.preventDefault();
    this.setState({
      mobileSearch: true,
    });
  }

  handleSearchNav(e) {
    e.preventDefault();
    this.setState(
      {
        mobileSearch: false,
      },
      function () {
        this.refs.searchBox.value = "";
        this.props.handleMobileSearch();
      }
    );
  }
  handleClickOutside(event) {
    const cartNode = findDOMNode(this.refs.cartPreview);
    const buttonNode = findDOMNode(this.refs.cartButton);
    // if (cartNode.classList.contains("active")) {
    //   if (!cartNode || !cartNode.contains(event.target)) {
    //     this.setState({
    //       showCart: false
    //     });
    //     event.stopPropagation();
    //   }
    // }
  }
  componentDidMount() {
    document.addEventListener(
      "click",
      this.handleClickOutside.bind(this),
      true
    );
  }
  componentWillUnmount() {
    document.removeEventListener(
      "click",
      this.handleClickOutside.bind(this),
      true
    );
  }
  render() {
    let cartItems;
    let searchBySku = (
      <input
        type="search"
        ref="searchBoxSku"
        placeholder="Search By Sku"
        className="search-keyword"
        onChange={this.props.handleSearchBySku}
      />
    );
    let searchByStoreId = (
      <input
        type="search"
        ref="searchStoreId"
        placeholder="Search By StoreId"
        className="search-keyword"
        onChange={this.props.handleSearchByStoreId}
      />
    );

    let adItem = (
      <button
        class="new-iten-btn"
        variant="primary"
        type="button"
        onClick={this.handleUpdate.bind(this)}
      >
        New Item
      </button>
    );

    let create = (
      <AddItem
        cancelEdit={this.cancelEdit}
        getAll={this.props.getAll}
        {...this.state}
      ></AddItem>
    );
    let showModal = !this.state.modal ? "" : create;
    let showSearchBySku =
      localStorage.getItem("type") === "admin" ? searchBySku : "";
    let showSearchByStoreId =
      localStorage.getItem("type") === "admin" ? searchByStoreId : "";
    let showAddItem = localStorage.getItem("type") === "admin" ? adItem : "";

    cartItems = this.state.cart.map((product) => {
      return (
        <li className="cart-item" key={product.name}>
          <img className="product-image" src={product.image} />
          <div className="product-info">
            <p className="product-name">{product.name}</p>
            <p className="product-price">{product.price}</p>
          </div>
          <div className="product-total">
            <p className="quantity">
              <button
                type="button"
                onClick={this.increment.bind(this, product)}
              >
                <b> + </b>
              </button>
              {product.qty} {product.qty > 1 ? "Nos." : "No."}{" "}
              <button
                disabled={!this.state.value}
                type="button"
                onClick={this.decrement.bind(this, product)}
              >
                <b> - </b>
              </button>
            </p>
            <p className="amount">{product.qty * product.price}</p>
          </div>
          <a
            className="product-remove"
            href="#"
            onClick={this.props.removeProduct.bind(this, product.id)}
          >
            ×
          </a>
        </li>
      );
    });
    let view;
    if (cartItems.length <= 0) {
      view = <EmptyCart />;
    } else {
      view = (
        <CSSTransitionGroup
          transitionName="fadeIn"
          transitionEnterTimeout={500}
          transitionLeaveTimeout={300}
          component="ul"
          className="cart-items"
        >
          {cartItems}
        </CSSTransitionGroup>
      );
    }
    let bag = (
      <div className="cart">
        <div className="cart-info">
          <table>
            <tbody>
              <tr>
                <td>No. of items</td>
                <td>:</td>
                <td>
                  <strong>{this.props.totalItems}</strong>
                </td>
              </tr>
              <tr>
                <td>Sub Total</td>
                <td>:</td>
                <td>
                  <strong>{this.props.total}</strong>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <a
          className="cart-icon"
          href="#"
          onClick={this.handleCart.bind(this)}
          ref="cartButton"
        >
          <img
            className={this.props.cartBounce ? "tada" : " "}
            src="https://res.cloudinary.com/sivadass/image/upload/v1493548928/icons/bag.png"
            alt="Cart"
          />
          {this.props.totalItems ? (
            <span className="cart-count">{this.props.totalItems}</span>
          ) : (
            ""
          )}
        </a>
        <div
          className={
            this.state.showCart ? "cart-preview active" : "cart-preview"
          }
          ref="cartPreview"
        >
          <CartScrollBar>{view}</CartScrollBar>
          <div className="action-block">
            <Link
              to={{
                pathname: "/homePooler/checkout/",
                state: {
                  id: this.props.storeid,
                  store: this.props.storeid,
                  pool: "",
                  qty: this.props.totalItems,
                  items: this.props.cart,
                  price: this.props.totalAmount,
                  finalPrice: "",
                  available: true,
                  forDelivery: true,
                  status: "Placed",
                  orderOwner: "",
                  deliveryBy: null,
                },
              }}
            >
              <button
                type="button"
                className={this.state.cart.length > 0 ? " " : "disabled"}
              >
                PROCEED TO CHECKOUT
              </button>
            </Link>
          </div>
        </div>
      </div>
    );
    let showCart = localStorage.getItem("type") === "pooler" ? bag : "";
    return (
      <header>
        {showModal}
        <div className="container">
          <div className="brand">{showAddItem}</div>

          <div className="search">
            <a
              className="mobile-search"
              href="#"
              onClick={this.handleMobileSearch.bind(this)}
            >
              <img
                src="https://res.cloudinary.com/sivadass/image/upload/v1494756966/icons/search-green.png"
                alt="search"
              />
            </a>
            <form
              action="#"
              method="get"
              className={
                this.state.mobileSearch ? "search-form active" : "search-form"
              }
            >
              <a
                className="back-button"
                href="#"
                onClick={this.handleSearchNav.bind(this)}
              >
                <img
                  src="https://res.cloudinary.com/sivadass/image/upload/v1494756030/icons/back.png"
                  alt="back"
                />
              </a>
              <input
                type="search"
                ref="searchBox"
                placeholder="Search By Name"
                className="search-keyword"
                onChange={this.props.handleSearch}
              />
              {showSearchBySku}
              {showSearchByStoreId}

              <button
                className="search-button"
                type="submit"
                onClick={this.handleSubmit.bind(this)}
              />
            </form>
          </div>
          {showCart}
        </div>
      </header>
    );
  }
}

export default Header;
