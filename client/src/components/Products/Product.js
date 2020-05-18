import React, { Component } from "react";
import Counter from "./Counter";
import EditItem from "../AdminPages/EditItem";
import axios from "axios";
import "./product.css";

class Product extends Component {
  constructor(props) {
    super(props);
    console.log(props);
    this.state = {
      selectedProduct: {},
      quickViewProduct: {},
      isAdded: false,
      modal: false,
    };
    this.handleUpdate = this.handleUpdate.bind(this);
  }
  addToCart(image, name, price, id, weight, quantity, unit, sku, storeId) {
    console.log("add to cart in  props", quantity);
    this.setState(
      {
        selectedProduct: {
          image: image,
          name: name,
          price: price,
          id: id,
          weight: weight,
          qty: quantity,
          unit: unit,
          sku: sku,
          storeId: storeId,
        },
      },
      function () {
        if (this.state.selectedProduct.qty > 0) {
          console.log("item added", this.state.selectedProduct);
          this.props.addToCart(this.state.selectedProduct);
        }
      }
    );
    this.setState(
      {
        isAdded: this.state.selectedProduct.qty > 0 ? true : false,
      },
      function () {
        setTimeout(() => {
          this.setState({
            isAdded: false,
            selectedProduct: {},
          });
        }, 3500);
      }
    );
  }

  handleUpdate(
    image,
    name,
    price,
    id,
    weight,
    quantity,
    unit,
    sku,
    storeId,
    brand,
    flag,
    description
  ) {
    console.log("add to cart in  props");
    this.setState(
      {
        selectedProduct: {
          image: image,
          name: name,
          price: price,
          id: id,
          weight: weight,
          quantity: quantity,
          unit: unit,
          sku: sku,
          storeId: storeId,
          flag: flag,
          brand: brand,
          description: description
        },
      },
      function () {
        let adminId = localStorage.getItem("id");
        if (this.state.selectedProduct.flag === 0) {
          this.setState({
            modal: true,
          });
          console.log("handleupdate", this.state.selectedProduct);
          this.props.getAll();
        } else {
          axios
            .delete(`http://localhost:8080/product/${id}/${adminId}`, null)
            .then((response) => {
              console.log("create data res", response);
            })
            .catch((err) => {
              console.error(err);
            });
          console.log("handleDelete", this.state.selectedProduct);
        }
      }
    );
  }

  quickView(image, name, price, id) {
    this.setState(
      {
        quickViewProduct: {
          image: image,
          name: name,
          price: price,
          id: id,
        },
      },
      function () {
        this.props.openModal(this.state.quickViewProdcut);
      }
    );
  }
  render() {
    let image = this.props.image;
    let name = this.props.name;
    let price = this.props.price;
    let id = this.props.sku;
    let unit = this.props.unit;
    let sku = this.props.sku;
    let storeId = this.props.storeId;
    let weight = this.props.weight;
    let quantity = this.props.productQuantity;
    let brand = this.props.brand;
    let description = this.props.description;
    let counter = (
      <Counter
        productQuantity={quantity}
        updateQuantity={this.props.updateQuantity}
        resetQuantity={this.resetQuantity}
      />
    );

    let cart = (
      <div className="product-action">
        <button
          className={!this.state.isAdded ? "add-to-cart-button" : "added"}
          type="button"
          onClick={this.addToCart.bind(
            this,
            image,
            name,
            price,
            id,
            weight,
            quantity,
            unit,
            sku,
            storeId
          )}
        >
          {!this.state.isAdded ? "ADD TO CART" : "✔ ADDED"}
        </button>
      </div>
    );

    let edit = (
      <div className="product-action">
        <button
          variant="primary"
          type="button"
          onClick={this.handleUpdate.bind(
            this,
            image,
            name,
            price,
            id,
            weight,
            quantity,
            unit,
            sku,
            storeId,
            brand,
            0,
            description,
          )}
        >
          EDIT
        </button>
      </div>
    );

    let editModel = (
      <EditItem cancelEdit={this.state.cancelEdit} {...this.state}></EditItem>
    );

    let del = (
      <div className="product-action">
        <button
          variant="primary"
          type="button"
          onClick={this.handleUpdate.bind(
            this,
            image,
            name,
            price,
            id,
            weight,
            quantity,
            unit,
            sku,
            storeId,
            brand,
            1
          )}
        >
          DELETE
        </button>
      </div>
    );

    let editQty = localStorage.getItem("type") === "pooler" ? counter : "";
    let cartEle = localStorage.getItem("type") === "pooler" ? cart : "";
    let showEdit = localStorage.getItem("type") === "admin" ? edit : "";
    let showDel = localStorage.getItem("type") === "admin" ? del : "";
    let showModal = !this.state.modal ? "" : editModel;
    return (
      <div className="product">
        <div className="product-image">
          <img
            src={image}
            alt={this.props.name}
            onClick={this.quickView.bind(
              this,
              image,
              name,
              price,
              id,
              weight,
              quantity,
              unit,
              sku,
              storeId
            )}
          />
        </div>
        <h4 className="product-name">
          <div>
            {this.props.name}
          </div>
          <div>
            {this.props.weight}  {this.props.unit}
          </div>
        </h4>
        <p className="product-price">{this.props.price}</p>

        {editQty}
        {cartEle}
        {showEdit}
        {showDel}
        {showModal}
      </div>
    );
  }
}

export default Product;
