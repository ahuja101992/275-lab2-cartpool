import React, { Component } from "react";
import Product from "./Product";
import LoadingProducts from "./loaders/Products";
import NoResults from "./NoResults";
import CSSTransitionGroup from "react-transition-group/CSSTransitionGroup";

class Products extends Component {
  constructor() {
    super();
  }
  render() {
    let productsData;
    let term = this.props.searchTerm;
    let storeId = this.props.storeId;
    let sku = this.props.sku;
    let x;

    function searchingFor(term) {
      return function(x) {
        return x.name.toLowerCase().includes(term.toLowerCase()) || !term;
      };
    }

    function searchingForId(storeId) {
      return function(x) {
        return storeId.name.toLowerCase().includes(storeId.toLowerCase()) || !storeId;
      };
    }

    function searchingForSku(sku) {
      return function(x) {
        return sku.name.toLowerCase().includes(storeId.toLowerCase()) || !sku;
      };
    }

    productsData = this.props.productsList
      .filter(searchingFor(term))
      .map(product => {
        return (
          <Product
            key={product.sku}
            price={product.price}
            name={product.name}
            image={product.imageURL}
            id={product.id.storeId}
            sku={product.id.sku}
            addToCart={this.props.addToCart}
            productQuantity={this.props.productQuantity}
            updateQuantity={this.props.updateQuantity}
            openModal={this.props.openModal}
          />
        );
      });

    // Empty and Loading States
    let view;
    if (productsData.length <= 0 && !term) {
      view = <LoadingProducts />;
    } else if (productsData.length <= 0 && term) {
      view = <NoResults />;
    } else {
      view = (
        <CSSTransitionGroup
          transitionName="fadeIn"
          transitionEnterTimeout={500}
          transitionLeaveTimeout={300}
          component="div"
          className="products"
        >
          {productsData}
        </CSSTransitionGroup>
      );
    }
    return <div className="products-wrapper">{view}</div>;
  }
}

export default Products;
