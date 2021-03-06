import React, {Component} from "react";
import Product from "./Product";
import NoResults from "./NoResults";
import NoProducts from "./NoProducts";
import CSSTransitionGroup from "react-transition-group/CSSTransitionGroup";

class Products extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        let productsData;
        let term = this.props.searchTerm;
        let storeId = this.props.storeId;
        let sku = this.props.sku;
        let x;

        function searchingFor(term) {
            return function (x) {
                return x.name.toLowerCase().includes(term.toLowerCase()) || !term;
            };
        }

        function searchingForId(term) {
            console.log("storeid", term);
            return function (x) {
                return x.id.storeId == term || !term;
            };
        }

        function searchingForSku(term) {
            console.log("sku", term);
            return function (x) {
                return x.id.sku.toLowerCase().includes(term.toLowerCase()) || !term;
            };
        }

        console.log("search", this.props.flag);
        let func = searchingFor(term)

        if (this.props.flag === 1) {
            func = searchingForSku(term)
        }

        if (this.props.flag === 2) {
            func = searchingForId(term)
        }
        productsData = this.props.productsList
            .filter(func)
            .map(product => {
                return (
                    <Product
                        key={product.sku}
                        price={product.price}
                        weight={product.qty}
                        unit={product.unit}
                        name={product.name}
                        image={product.imageURL}
                        id={product.id.sku}
                        storeId={product.id.storeId}
                        sku={product.id.sku}
                        brand={product.brandName}
                        addToCart={this.props.addToCart}
                        productQuantity={this.props.productQuantity}
                        updateQuantity={this.props.updateQuantity}
                        openModal={this.props.openModal}
                        getAll={this.props.getAll}
                        description={product.description}
                    />
                );
            });

        // Empty and Loading States
        let view;
        if (productsData.length <= 0 && !term) {
            view = <NoProducts/>
            // <div><h2>No products in the store</h2>
            // </div>
        } else if (productsData.length <= 0 && term.length > 0) {
            view = <NoResults/>;
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
