import React, { Component } from "react";
import { Form, Modal, Button } from "react-bootstrap";
import "./profile.css";
import axios from 'axios';
import { Link } from "react-router-dom";
import { Multiselect } from "multiselect-react-dropdown";
// import "../node_modules/bootstrap/dist/css/bootstrap.min.css";

class EditItem extends Component {
    constructor(props) {
        super(props);
        this.state = {
            editProfile: true, //for modal
            selectedCoverPic: null,
            selectedProfilePic: null,
            openFollower: false,
            users: [],
            openFollowee: false,
            plainArray: [],
            objectArray: [],
            selectedValues: [],
            name:this.props.selectedProduct.name,
            brand:this.props.selectedProduct.brand,
            desc:this.props.selectedProduct.desc,
            qty:this.props.selectedProduct.quantity,
            unit:this.props.selectedProduct.unit,
            price:this.props.selectedProduct.price
        };
    }
    
    onSelect=(selectedList, selectedItem) =>{
        this.state.selectedValues = selectedList;
        console.log(this.state.selectedValues);
    }
     
    addItem= (val) =>{
        console.log("val",val);
        this.setState({
            selectedValues : this.state.selectedValues.push(val)
        })
        console.log("Selected val",this.state.selectedValues);
      }

    editProfile = () => {
        this.setState({ editProfile: true });
    };


    cancelEdit = () => {
        this.setState({ editProfile: false });
    };
    onCoverPicUploadHandler = (event) => {
        this.setState({
            selectedCoverPic: event.target.files[0]
        });
    };
    onProfilePicUploadHandler = (event) => {
        this.setState({
            selectedProfilePic: event.target.files[0]
        });
    };
    saveProfile = (e) => {
        // save profile code
        e.preventDefault();
        console.log("Inside createStore",e.target.length)
        const data = new FormData();
        for (let i = 0; i < e.target.length; i++) {
            if (e.target[i].id !== "") {
                console.log(e.target[i].id);
                console.log(e.target[i].value);
                data[e.target[i].id] = e.target[i].value;
            }
        }
        let store_arr=[];
        this.state.selectedValues.forEach(ele=>{
            store_arr.push(ele.value);
        });

        console.log("store_arr",store_arr);
        let updatedData = {
            stores: store_arr.toString(),
            name: data.name,
            desc: data.desc,
            brand: data.brand ? data.brand : "",
            unit: data.unit,
            qty: data.qty,
            price:data.price,
            adminId:1
        }
    }

    render() {
        console.log("checking props", JSON.stringify(this.props));
        let usrDetails = this.props.userDetails ? this.props.userDetails : [];
        let usrTweets = this.props.userTweets ? this.props.userTweets : [];
        let userData = usrDetails.data ? usrDetails.data : [];
        return (
            <div class="profile-container col-sm-12">
                <div class="profile-pic-btn-container row">
                </div>
                
                <Modal
                    show={this.state.editProfile}
                    onHide={this.cancelEdit}
                    animation={false}
                    scrollable={true}
                >
                    <Modal.Header closeButton>
                        <Modal.Title>Product Info</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <div class="edit-profile-continer">
                            <div class="cover-pic-container row">
                                <input
                                    class="profile-pic-btn"
                                    type="file"
                                    accept="image/*"
                                    id="cover-pic-upload"
                                    onChange={this.onCoverPicUploadHandler}
                                ></input>

                                <label for="cover-pic-upload">
                                    <img
                                        src={userData.coverPic ? userData.coverPic : require("../../static/images/cover_pic1.png")}
                                        width="100%"
                                        height="180px"
                                    />
                                </label>
                            </div>
                        </div>
                        <div class="edit-details-form">
                        <Form onSubmit={this.saveProfile}>
                                <Form.Group controlId="name">
                                    <Form.Label>Name</Form.Label>
                                    <Form.Control
                                    type="text"
                                        onChange={e => this.setState({ name: e.target.value })}
                                        placeholder={this.state.name}
                                        // required
                                        value={this.state.name}
                                    />
                                </Form.Group>
                                <Form.Group controlId="brand">
                                    <Form.Label>Brand</Form.Label>
                                    <Form.Control
                                        // required
                                        type="text"
                                        onChange={e => this.setState({ brand: e.target.value })}
                                        placeholder={this.state.brand}
                                        value={this.state.brand}
                                    />
                                </Form.Group>
                                <Form.Group controlId="desc">
                                    <Form.Label>Description</Form.Label>
                                    <Form.Control
                                        // required 
                                        type="text"
                                        as="textarea"
                                        rows="3"
                                        onChange={e => this.setState({ desc: e.target.value })}
                                        placeholder={this.state.desc}
                                        value={this.state.desc}
                                    />
                                </Form.Group>

                                <Form.Group controlId="qty">
                                    <Form.Label>Quantity</Form.Label>
                                    <Form.Control 
                                    // required   
                                    type="text"
                                    onChange={e => this.setState({ qty: e.target.value })}
                                    placeholder={this.state.qty}
                                    value={this.state.qty}
                                    />
                                </Form.Group>

                                <Form.Group controlId="unit">
                                <Form.Label>Unit</Form.Label>
                                 <Form.Control as="select" required>
                                <option>gram</option>
                                 <option>ounze</option>
                                 <option>piece</option>
                                 <option>gallon</option>
                                 <option>litres</option>
                                 <option>mili Litres</option>
                                 <option>kilo-gram</option>
                                 </Form.Control>
                                </Form.Group>
                                <Form.Group controlId="price">
                                    <Form.Label>Price( in Dollars ) </Form.Label>
                                    <Form.Control   type="text" required 
                                    placeholder={"$"+this.state.price} 
                                    onChange={e => this.setState({ price: e.target.value })}
                                    value={"$"+this.state.price}
                                    />
                                </Form.Group>
                                <Button variant="primary" type="submit">
                                Submit
                            </Button>
                              </Form>
                        </div>
                    </Modal.Body>
                </Modal>
            </div>
        );
    }
}
export default EditItem;
