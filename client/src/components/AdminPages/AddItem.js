import React, { Component } from "react";
import { Form, Modal, Button } from "react-bootstrap";
import "./profile.css";
import axios from 'axios';
import { Link } from "react-router-dom";
import { Multiselect } from "multiselect-react-dropdown";
// import "../node_modules/bootstrap/dist/css/bootstrap.min.css";

class AddItem extends Component {
    constructor(props) {
        super(props);
        this.state = {
            editProfile: this.props.modal, //for modal
            selectedCoverPic: null,
            selectedProfilePic: null,
            openFollower: false,
            users: [],
            openFollowee: false,
            plainArray: [],
            objectArray: [],
            selectedValues: [],
            imageUrl: "https://res.cloudinary.com/sivadass/image/upload/v1493620045/dummy-products/strawberry.jpg" 
        };
        this.onFileChange = this.onFileChange.bind(this);
    }
    componentWillMount = () => {
     this.setState({ editProfile: true });
        var  plainArray = [];
        var  objectArray = [];
        axios.get(`http://localhost:8080/inventory/store/getByAdmin/1`)
        .then(response => {
            console.log("store response",response);
            if(response.data){
                let arr = response.data;
                let text =[]
                let obj =[];
                arr.forEach(item => {
                    console.log("item",item);
                    let container = {
                        key : item.name,
                        value : item.id
                    };
                   text.push(item.name);
                   obj.push(container);
                });

                console.log("text::",text);
                console.log("obj::",obj);
                this.setState({
                    plainArray : text,
                    objectArray : obj
                })
            }
        })
        .catch(err => {
            console.error(err);
        });
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
        this.props.cancelEdit();
        this.setState({ editProfile: true });
    };

    componentDidMount() {
        this.setState({ editProfile: true });
        // const email = localStorage.getItem("email_id");
        // const data = {
        //     user_id: 100
        // };
        // this.props.getProfileDetails(data);
    }

    cancelEdit = () => {
        this.setState({
            editProfile :false
        })
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
    onFileChange(files) {
        if (files == null || files.length == 0) return;
        let file = files[0];

        const data = new FormData();
        data.append("file", file, file.name);

        let user_id = localStorage.getItem('user_id');
        axios.post(`http://localhost:8080/storage/uploadFile`, data)
            .then(res => {
                if (res.status === 200) {
                    this.setState({ imageUrl: res.data });
                }
            })
            .catch(err => console.error(err));
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
            image_url:this.state.imageUrl,
            adminId:localStorage.getItem("id")
        }
        console.log("updated image",updatedData);
        axios.post(`http://localhost:8080/product/create`, null, {params: updatedData})
        .then((response) => {
           console.log("create data res",response)
        }).catch(err => {
            console.error(err);
        });
       

        this.props.cancelEdit();
        this.props.getAll();
        alert("Product created succesfully")
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
                    show={this.props.modal}
                    onHide={this.props.cancelEdit}
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
                                    onChange={(e) => this.onFileChange(e.target.files)}
                                ></input>

                                <label for="cover-pic-upload">
                                    <img
                                        src={this.state.imageUrl}
                                        width="100%"
                                        height="180px"
                                    />
                                </label>
                            </div>
                        </div>
                        <div class="edit-details-form">
                        <Form onSubmit={this.saveProfile}>
                                <Multiselect
              options={this.state.objectArray}
              displayValue="key"
              onSelect={this.onSelect} // Function will trigger on select event
              selectedValues={this.state.selectedValues}
              placeholder="Select Stores"
            />
                                <Form.Group controlId="name">
                                    <Form.Label>Name</Form.Label>
                                    <Form.Control
                                    type="text"
                                        // onChange={e => this.setState({ last_name: e.target.value })}
                                        placeholder={usrDetails.firstName ? usrDetails.firstName : ""}
                                        required
                                        // value={this.props.firstName + " " + this.props.lastName}
                                    />
                                </Form.Group>
                                <Form.Group controlId="brand">
                                    <Form.Label>Brand</Form.Label>
                                    <Form.Control
                                        required
                                        type="text"
                                        // onChange={e => this.setState({ last_name: e.target.value })}
                                        placeholder={usrDetails.lastName ? usrDetails.lastName : ""}
                                    // value={this.props.firstName + " " + this.props.lastName}
                                    />
                                </Form.Group>
                                <Form.Group controlId="desc">
                                    <Form.Label>Description</Form.Label>
                                    <Form.Control
                                        required 
                                        type="text"
                                        as="textarea"
                                        rows="3"
                                        placeholder={userData.bio ? userData.bio : "Add your Bio"}
                                    />
                                </Form.Group>

                                <Form.Group controlId="qty">
                                    <Form.Label>Quantity</Form.Label>
                                    <Form.Control 
                                    min="0.00"
                                    required   
                                    type="number"
                                        // onChange={e => this.setState({ last_name: e.target.value })}
                                    // value={this.props.firstName + " " + this.props.lastName}
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
                                    <Form.Control type="number"
                                    min="0.00"
                                    max="100.00"
                                     required 
                                    placeholder={userData.website ? userData.website : "$" } />
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
export default AddItem;
