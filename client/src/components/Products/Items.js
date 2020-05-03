import React, {Component} from "react";
import {Button, Card, Col, Form, Modal} from "react-bootstrap";
import {connect} from "react-redux";
import {createStore, deleteStore, getStoresByAdmin} from "../../redux/actions/inventoryActions";
import axios from "axios";
import Header from "./Header";
import Products from "./Products";
import Footer from "./Footer";
import QuickView from "./QuickView";
import "./scss/style.scss";

function mapStateToProps(store) {
    return {
        stores: store.inventory.stores,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        getStoresByAdmin: (payload) => dispatch(getStoresByAdmin(payload)),
        createStore: (payload) => dispatch(createStore(payload)),
        deleteStore: (payload) => dispatch(deleteStore(payload)),
    };
}

class Items extends Component {
  constructor(props) {
    super(props);
        this.state = {
          storeid: localStorage.getItem('type') === "pooler" ? props.location.state.id : 1,
          store:  localStorage.getItem('type') === "pooler" ? props.location.state.store : 1,
          products: [],
          cart: [],
          totalItems: 0,
          totalAmount: 0,
          term: "",
          category: "",
          cartBounce: false,
          quantity: 0,
          quickViewProduct: {},
          modalActive: false
        };
        this.handleSearch = this.handleSearch.bind(this);
        this.handleMobileSearch = this.handleMobileSearch.bind(this);
        this.handleCategory = this.handleCategory.bind(this);
        this.handleAddToCart = this.handleAddToCart.bind(this);
        this.sumTotalItems = this.sumTotalItems.bind(this);
        this.sumTotalAmount = this.sumTotalAmount.bind(this);
        this.checkProduct = this.checkProduct.bind(this);
        this.updateQuantity = this.updateQuantity.bind(this);
        this.handleRemoveProduct = this.handleRemoveProduct.bind(this);
        this.openModal = this.openModal.bind(this);
        this.closeModal = this.closeModal.bind(this);
      }
      // Fetch Initial Set of Products from external API
      getProducts() {
        let url =
          "https://res.cloudinary.com/sivadass/raw/upload/v1535817394/json/products.json";
        axios.get(url).then(response => {
          console.log(response);
          this.setState({
            products: response.data
          });
        });
      }

      getAll(){
        if(localStorage.getItem('type') === "pooler" ){
        axios.get(`http://localhost:8080/products/${this.state.storeid}`)
      .then((response) => {
         console.log("create data res",response)
         this.setState({
          products: response.data
            });
      }).catch(err => {
          console.error(err);
      })
    }else{
      axios.get(`http://localhost:8080/products/groupByName`)
      .then((response) => {
         console.log("create data res",response)
         this.setState({
          products: response.data
            });
      }).catch(err => {
          console.error(err);
      })
    }
    }
      componentWillMount() {
        this.getAll();
      }
    
      // Search by Keyword
      handleSearch(event) {
        this.setState({ term: event.target.value });
      }
      // Mobile Search Reset
      handleMobileSearch() {
        this.setState({ term: "" });
      }
      // Filter by Category
      handleCategory(event) {
        this.setState({ category: event.target.value });
        console.log(this.state.category);
      }
      // Add to Cart
      handleAddToCart(selectedProducts) {
        let cartItem = this.state.cart;
        let productID = selectedProducts.id;
        let productQty = selectedProducts.qty;
        if (this.checkProduct(productID)) {
          console.log("hi");
          let index = cartItem.findIndex(x => x.id == productID);
          cartItem[index].quantity =
            Number(cartItem[index].qty) + Number(productQty);
          this.setState({
            cart: cartItem
          });
        } else {
          cartItem.push(selectedProducts);
        }
        this.setState({
          cart: cartItem,
          cartBounce: true
        });
        setTimeout(
          function() {
            this.setState({
              cartBounce: false,
              quantity: 0
            });
            console.log(this.state.quantity);
            console.log(this.state.cart);
          }.bind(this),
          1000
        );
        this.sumTotalItems(this.state.cart);
        this.sumTotalAmount(this.state.cart);
      }
      handleRemoveProduct(id, e) {
        let cart = this.state.cart;
        let index = cart.findIndex(x => x.id == id);
        cart.splice(index, 1);
        this.setState({
          cart: cart
        });
        this.sumTotalItems(this.state.cart);
        this.sumTotalAmount(this.state.cart);
        e.preventDefault();
      }
      checkProduct(productID) {
        let cart = this.state.cart;
        return cart.some(function(item) {
          return item.id === productID;
        });
      }
      sumTotalItems() {
        let total = 0;
        let cart = this.state.cart;
        total = cart.length;
        this.setState({
          totalItems: total
        });
      }
      sumTotalAmount() {
        let total = 0;
        let cart = this.state.cart;
        for (var i = 0; i < cart.length; i++) {
          total += cart[i].price * parseInt(cart[i].qty);
        }
        this.setState({
          totalAmount: total
        });
      }
    
      //Reset Quantity
      updateQuantity(qty) {
        console.log("quantity added...");
        this.setState({
          quantity: qty
        });
      }
      // Open Modal
      openModal(product) {
        this.setState({
          quickViewProduct: product,
          modalActive: true
        });
      }
      // Close Modal
      closeModal() {
        this.setState({
          modalActive: false
        });
      }
    
      handleUpdate(selectedProducts){
        console.log("edit")
      }

      handleDelete(selectedProducts){
        console.log("delete")
      }
      render() {
        let header=<Header
        cartBounce={this.state.cartBounce}
        total={this.state.totalAmount}
        totalItems={this.state.totalItems}
        cartItems={this.state.cart}
        removeProduct={this.handleRemoveProduct}
        handleSearch={this.handleSearch}
        handleMobileSearch={this.handleMobileSearch}
        handleCategory={this.handleCategory}
        categoryTerm={this.state.category}
        updateQuantity={this.updateQuantity}
        productQuantity={this.state.moq}
        {...this.state}
      />

      let showHeader = localStorage.getItem('type') === "pooler" ? header : "";
        return (
          <div className="container">
            {showHeader}
            <Products
              productsList={this.state.products}
              searchTerm={this.state.term}
              addToCart={this.handleAddToCart}
              productQuantity={this.state.quantity}
              updateQuantity={this.updateQuantity}
              openModal={this.openModal}
            />
            {/* <Footer /> */}
            {/* <QuickView
              product={this.state.quickViewProduct}
              openModal={this.state.modalActive}
              closeModal={this.closeModal}
            /> */}
          </div>
        );
      }
    }

export default connect(mapStateToProps, mapDispatchToProps)(Items);