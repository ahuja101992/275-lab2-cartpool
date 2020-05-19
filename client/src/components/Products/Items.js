import React, {Component} from "react";
import {Button, Form, Modal} from "react-bootstrap";
import {connect} from "react-redux";
import {createStore, deleteStore, getStoresByAdmin,} from "../../redux/actions/inventoryActions";
import axios from "axios";
import Header from "./Header";
import Products from "./Products";
import "./scss/style.scss";
import {HOSTNAME} from "../../constants/appConstants";

function mapStateToProps(store) {
    return {
        stores: store.inventory.stores,
    };
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
            poolerid : localStorage.getItem("id"),
            storeid:
                this.props.location.state == null ? 1 : this.props.location.state.id,
            products: [],
            cart: JSON.parse(localStorage.getItem("cart") || "[]"),
            totalItems: 0,
            totalAmount: 0,
            term: "",
            category: "",
            cartBounce: false,
            quantity: 0,
            quickViewProduct: {},
            modalActive: false,
            erroModal: false,
            flag: 0,
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
        this.handleSearchByStoreId = this.handleSearchByStoreId.bind(this);
        this.handleSearchBySku = this.handleSearchBySku.bind(this);
        this.addOne = this.addOne.bind(this);
        this.deleteOne = this.deleteOne.bind(this);
        this.handleCartState = this.handleCartState.bind(this);
        this.refreshCartState = this.refreshCartState.bind(this);
        this.handleCloseCartModal = this.handleCloseCartModal.bind(this);
    }

    // Fetch Initial Set of Products from external API
    getProducts() {
        this.setItem({
            cart: JSON.parse(localStorage.getItem("cart") || "[]"),
        });
        this.sumTotalItems(this.state.cart);
        this.sumTotalAmount(this.state.cart);
        let url =
            "https://res.cloudinary.com/sivadass/raw/upload/v1535817394/json/products.json";
        axios.get(url).then((response) => {
            console.log(response);
            this.setState({
                products: response.data,
            });
        });
    }

    async getAll() {
       await axios
            .get(`http://${HOSTNAME}:8080/products/${this.state.storeid}`)
            .then((response) => {
                console.log("create data res", response);
                this.setState({
                    products: response.data,
                });
            })
            .catch((err) => {
                console.error(err);
            });
       await axios
            .get(`http://${HOSTNAME}:8080/pooler/profile/getById/${this.state.poolerid}`)
            .then((response) => {
                if(response.data!=null && response.data.pool!=null){
                  localStorage.setItem("pooler","yes");
                }else{
                  alert("To add item to cart, please join some pool and Refresh this page!!")
                  localStorage.setItem("pooler","no");
                }
            })
            .catch((err) => {
                console.error(err);
            });

        this.setState({
            cart: JSON.parse(localStorage.getItem("cart") || "[]")
        })
        this.sumTotalItems(this.state.cart);
        this.sumTotalAmount(this.state.cart);
    }

    componentWillMount() {
        this.getAll();
    }

    // Search by Keyword
    handleSearch(event) {
        this.setState({term: event.target.value, flag: 0});
    }

    // Search by sKU
    handleSearchBySku(event) {
        console.log("sku parent page");
        this.setState({term: event.target.value, flag: 1});
    }

    // Search by StoreId
    handleSearchByStoreId(event) {
        console.log("store parent page");
        this.setState({term: event.target.value, flag: 2});
    }

    // Mobile Search Reset
    handleMobileSearch() {
        this.setState({term: ""});
    }

    // Filter by Category
    handleCategory(event) {
        this.setState({category: event.target.value});
        console.log(this.state.category);
    }

    handleCartState() {
        this.setState({
            erroModal: false,
        });
    }

    handleCloseCartModal() {
        this.setState({
            erroModal: false,
        });
    }

    refreshCartState(e) {
        e.preventDefault();
        this.setState({
            erroModal: false,
            cartItem: [],
        });
        localStorage.removeItem("cart");
        localStorage.removeItem("cart-storeId");
    }

    // Add to Cart
    handleAddToCart(selectedProducts) {
        let cartItem = this.state.cart;
        console.log("check", selectedProducts);
        let productID = selectedProducts.id;
        let storeId = selectedProducts.storeId;
        let productQty = selectedProducts.qty;
        if (cartItem.length > 0) {
            console.log("storedif", storeId);
            if (storeId != localStorage.getItem("cart-storeId")) {
                this.setState({
                    erroModal: true,
                });
            }
        } else {
            localStorage.setItem("cart-storeId", selectedProducts.storeId);
        }
        if (this.checkProduct(productID)) {
            console.log("hi");
            let index = cartItem.findIndex((x) => x.id == productID);
            console.log("index", index, cartItem[index].qty);
            cartItem[index].qty = Number(cartItem[index].qty) + Number(productQty);
            this.setState({
                cart: cartItem,
            });
        } else {
            cartItem.push(selectedProducts);
        }
        this.setState({
            cart: cartItem,
            cartBounce: true,
        });

        localStorage.setItem("cart", JSON.stringify(cartItem));
        this.setState({
            cartBounce: false,
            quantity: 0,
        });
        console.log(this.state.quantity);
        console.log(this.state.cart);
        this.sumTotalItems(this.state.cart);
        this.sumTotalAmount(this.state.cart);
    }

    // Add to Cart
    addOne(selectedProducts) {
        let cartItem = this.state.cart;
        let productID = selectedProducts.id;
        let productQty = selectedProducts.qty;
        console.log(productID + ":::" + productQty);
        if (this.checkProduct(productID)) {
            console.log("hi");
            let index = cartItem.findIndex((x) => x.id == productID);
            console.log("index", index, cartItem[index].qty);
            cartItem[index].qty = Number(cartItem[index].qty) + 1;
            this.setState({
                cart: cartItem,
            });
        } else {
            cartItem.push(selectedProducts);
        }
        this.setState({
            cart: cartItem,
            cartBounce: true,
        });
        localStorage.setItem("cart", JSON.stringify(cartItem));
        setTimeout(
            function () {
                this.setState({
                    cartBounce: false,
                    quantity: 0,
                });
                console.log(this.state.quantity);
                console.log(this.state.cart);
            }.bind(this),
            1000
        );
        this.sumTotalItems(this.state.cart);
        this.sumTotalAmount(this.state.cart);
    }

    // Add to Cart
    deleteOne(selectedProducts) {
        let cartItem = this.state.cart;
        let productID = selectedProducts.id;
        let productQty = selectedProducts.qty;
        console.log(productID + ":::" + productQty);
        if (this.checkProduct(productID)) {
            console.log("hi");
            let index = cartItem.findIndex((x) => x.id == productID);
            console.log("index", index, cartItem[index].qty);
            cartItem[index].qty = Number(cartItem[index].qty) - 1;
            this.setState({
                cart: cartItem,
            });
        } else {
            cartItem.push(selectedProducts);
        }
        this.setState({
            cart: cartItem,
            cartBounce: true,
        });
        localStorage.setItem("cart", JSON.stringify(cartItem));
        setTimeout(
            function () {
                this.setState({
                    cartBounce: false,
                    quantity: 0,
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
        let index = cart.findIndex((x) => x.id == id);
        cart.splice(index, 1);
        this.setState({
            cart: cart,
        });
        localStorage.setItem("cart", JSON.stringify(cart));
        this.sumTotalItems(this.state.cart);
        this.sumTotalAmount(this.state.cart);
        e.preventDefault();
    }

    checkProduct(productID) {
        let cart = this.state.cart;
        return cart.some(function (item) {
            return item.id === productID;
        });
    }

    sumTotalItems() {
        let total = 0;
        let cart = this.state.cart;
        total = cart.length;
        this.setState({
            totalItems: total,
        });
    }

    sumTotalAmount() {
        let total = 0;
        let cart = this.state.cart;
        for (var i = 0; i < cart.length; i++) {
            total += cart[i].price * parseInt(cart[i].qty);
        }
        this.setState({
            totalAmount: total,
        });
    }

    //Reset Quantity
    updateQuantity(qty) {
        console.log("quantity added...");
        this.setState({
            quantity: qty,
        });
    }

    // Open Modal
    openModal(product) {
        this.setState({
            quickViewProduct: product,
            modalActive: true,
        });
    }

    // Close Modal
    closeModal() {
        this.setState({
            modalActive: false,
        });
    }

    handleUpdate(selectedProducts) {
        console.log("edit");
    }

    handleDelete(selectedProducts) {
        console.log("delete");
    }

    render() {
        let cartErrModal = (
            <Modal show={this.state.erroModal} onHide={this.handleCloseCartModal}>
                <Modal.Header>
                    <Modal.Title>Edit store</Modal.Title>
                </Modal.Header>

                <Form onSubmit={this.refreshCartState}>
                    <div className="text-muted text-center join-pool-modal-text">
                        Your cart contains items from other store. Would you like to reset
                        your cart for adding items from this store?
                    </div>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={this.handleCartState}>
                            NO
                        </Button>
                        <Button variant="primary" type="submit">
                            YES,START AFRESH
                        </Button>
                    </Modal.Footer>
                </Form>
            </Modal>
        );
        let header = (
            <Header
                cartBounce={this.state.cartBounce}
                total={this.state.totalAmount}
                totalItems={this.state.totalItems}
                cartItems={this.state.cart}
                removeProduct={this.handleRemoveProduct}
                handleSearch={this.handleSearch}
                handleSearchBySku={this.handleSearchBySku}
                handleSearchByStoreId={this.handleSearchByStoreId}
                handleMobileSearch={this.handleMobileSearch}
                handleCategory={this.handleCategory}
                categoryTerm={this.state.category}
                updateQuantity={this.updateQuantity}
                productQuantity={this.state.moq}
                addOne={this.addOne}
                deleteOne={this.deleteOne}
                {...this.state}
            />
        );

        return (
            <div className="container">
                {cartErrModal}
                {header}
                <Products
                    productsList={this.state.products}
                    searchTerm={this.state.term}
                    addToCart={this.handleAddToCart}
                    productQuantity={this.state.quantity}
                    updateQuantity={this.updateQuantity}
                    openModal={this.openModal}
                    flag={this.state.flag}
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
