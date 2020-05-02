import React, { Component } from "react";
import Counter from "./Counter";

class Product extends Component {
  constructor(props) {
    super(props);
    this.state = {
      selectedProduct: {},
      quickViewProduct: {},
      isAdded: false
    };
  }
  addToCart(image, name, price, id, quantity) {
    console.log("add to cart in  props");
    this.setState(
      {
        selectedProduct: {
          image: image,
          name: name,
          price: price,
          id: id,
          quantity: quantity
        }
      },
      function() {
        if(this.state.selectedProduct.quantity>0){
        this.props.addToCart(this.state.selectedProduct);
        }
      }
    );

    this.setState(
      {
        isAdded: this.state.selectedProduct.quantity>0 ? true :false
      },
      function() {
        setTimeout(() => {
          this.setState({
            isAdded: false,
            selectedProduct: {}
          });
        }, 3500);
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
          id: id
        }
      },
      function() {
        this.props.openModal(this.state.quickViewProdcut);
      }
    );
  }
  render() {
    let image = this.props.image;
    let name = this.props.name;
    let price = this.props.price;
    let id = this.props.sku;
    let storeId = this.props.id;
    let quantity = this.props.productQuantity;
    let counter = <Counter
    productQuantity={quantity}
    updateQuantity={this.props.updateQuantity}
    resetQuantity={this.resetQuantity}
  />
  
  let cart = <div className="product-action">
          <button
            className={!this.state.isAdded ? "" : "added"}
            type="button"
            onClick={this.addToCart.bind(
              this,
              image,
              name,
              price,
              id,
              quantity,
              storeId
            )}
          >
            {!this.state.isAdded ? "ADD TO CART" : "✔ ADDED"}
          </button>
        </div>

    
    let editQty = localStorage.getItem('type')=="pooler" ? counter : ""; 
    let cartEle = localStorage.getItem('type')=="pooler" ? cart : ""; 
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
              quantity,
              storeId
            )}
          />
        </div>
        <h4 className="product-name">{this.props.name}</h4>
        <p className="product-price">{this.props.price}</p>
        
        {editQty}
        {cartEle}
      </div>
    );
  }
}

export default Product;