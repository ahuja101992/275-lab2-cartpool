import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import './Profile.css';
import { HOSTNAME } from "../../constants/appConstants";
import { Button, Form, Modal, Row } from 'react-bootstrap'
import axios from 'axios';

class ProfilePage extends Component {
    constructor(props) {
        super(props);
        this.state = Object.assign({}, { firstName: "" }, { lastName: "" }, { email: "" }, { imageUrl: "https://bootdey.com/img/Content/avatar/avatar1.png" },
            { credits: 0 }, { screenName: "" }, { setShow: false }, { tempFirstName: "" }, { tempLastName: "" }, { tempEmail: "" }, { tempStreet: "" }, { tempCity: "" }, { tempState: "" }, { tempZip: "" },
            { address: { city: "", state: "", street: "", zip: "" } });

        this.onFileChange = this.onFileChange.bind(this);
    }

    componentDidMount() {
        //let poolerId = localStorage.getItem('id');
        let poolerId = 3;
        axios.get(`http://${HOSTNAME}:8080/pooler/profile/getById/${poolerId}`)
            .then(response => {
                console.log(response);
                this.setState({
                    firstName: response.data.firstName,
                    lastName: response.data.lastName,
                    email: response.data.email,
                    address: response.data.address ? { ...response.data.address } : "",
                    imageUrl: response.data.img ? response.data.img : "https://bootdey.com/img/Content/avatar/avatar1.png",
                    screenName: response.data.screenname,
                    credits: response.data.contribution,
                    tempFirstName: response.data.firstName,
                    tempLastName: response.data.lastName,
                    tempEmail: response.data.email,
                    tempStreet: response.data.address.street,
                    tempCity: response.data.address.city,
                    tempState: response.data.address.state,
                    tempZip: response.data.address.zip

                }, () => console.log(this.state));
            })
            .catch(error => {
                console.log(error);
            })
    }

    changeHandeler = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    };

    handleClose = () => {
        this.setState({
            setShow: false
        });
    }

    handleShow = () => {
        this.setState({
            setShow: true
        });
    }

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
    }

    submitChangeProfile = () => {
        //let poolerId = localStorage.getItem('id');
        let poolerId = 3;
        let payload = {
            firstName: this.state.tempFirstName,
            lastName: this.state.tempLastName,
            email: this.state.tempLastName,
            street: this.state.tempStreet,
            city: this.state.tempCity,
            state: this.state.tempState,
            zip: this.state.tempZip
        }
        axios.put(`http://${HOSTNAME}:8080/pooler/update/${poolerId}`, null, { params: payload })
            .then(response => {
                this.setState({
                    firstName: this.state.tempFirstName,
                    lastName: this.state.tempLastName,
                    email: this.state.tempLastName,
                    img: this.state.imageUrl,
                    street: this.state.tempStreet,
                    city: this.state.tempCity,
                    state: this.state.tempState,
                    zip: this.state.tempZip
                }, () => this.handleClose())
            })
            .catch(error => {
                console.log(error);
            })
    }

    render() {
        return (
            <React.Fragment>
                <div className="container">
                    <div className="view-account">
                        <section className="module">
                            <div className="module-inner">
                                <div className="side-bar">
                                    <div className="user-info">
                                        <img className="img-profile img-circle img-responsive center-block"
                                            src={this.state.imageUrl} alt="" />
                                        <ul className="meta list list-unstyled">
                                            <li className="name">{this.state.screenName}
                                                <label className="label label-info">{this.state.email}</label>
                                            </li>
                                        </ul>
                                    </div>
                                    <nav className="side-menu">
                                        <ul className="nav">
                                            <li className="active"><a href="#"><span
                                                className="fa fa-user"></span> Profile</a></li>
                                            <li><a href="#"><span className="fa fa-cog"></span> Pool Info</a></li>
                                            <li><a href="#"><span className="fa fa-credit-card"></span> Upcoming Orders</a>
                                            </li>
                                            <li><a href="#"><span className="fa fa-envelope"></span> Past Orders</a>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>
                                <div className="content-panel">
                                    <h2 className="title">Credits<span
                                        className="pro-label label label-warning">{this.state.credits}</span></h2>
                                    <form className="form-horizontal">
                                        <fieldset className="fieldset">
                                            <h3 className="fieldset-title text-muted">Personal Info</h3>
                                            <div className="form-group avatar">
                                                <figure className="figure col-md-2 col-sm-3 col-xs-12">
                                                    <img className="img-rounded img-responsive"
                                                        src={this.state.imageUrl} alt="" />
                                                </figure>
                                                <div className="form-inline col-md-10 col-sm-9 col-xs-12">
                                                    <input type="file" className="file-uploader pull-left" onChange={(e) => this.onFileChange(e.target.files)} />
                                                    <button type="submit"
                                                        className="btn btn-sm btn-default-alt pull-left">Update
                                                    Image
                                                    </button>
                                                </div>
                                            </div>
                                            <div className="form-group">
                                                <label className="col-md-2 col-sm-3 col-xs-12 control-label">Screen
                                                    Name</label>
                                                <div className="col-md-10 col-sm-9 col-xs-12">
                                                    <input type="text" className="form-control"
                                                        value={this.state.screenName} onChange={this.changeHandeler} />
                                                </div>
                                            </div>

                                            <div className="form-group">
                                                <label className="col-md-2 col-sm-3 col-xs-12 control-label">First
                                                    Name</label>
                                                <div className="col-md-10 col-sm-9 col-xs-12">
                                                    <input type="text" className="form-control"
                                                        value={this.state.firstName} onChange={this.changeHandeler} />
                                                </div>
                                            </div>
                                            <div className="form-group">
                                                <label className="col-md-2 col-sm-3 col-xs-12 control-label">Last
                                                    Name</label>
                                                <div className="col-md-10 col-sm-9 col-xs-12">
                                                    <input type="text" className="form-control"
                                                        value={this.state.firstName} onChange={this.changeHandeler} />
                                                </div>
                                            </div>
                                        </fieldset>
                                        <fieldset className="fieldset">
                                            <h3 className="fieldset-title text-muted">Contact Info</h3>
                                            <div className="form-group">
                                                <label
                                                    className="col-md-2  col-sm-3 col-xs-12 control-label">Email</label>
                                                <div className="col-md-10 col-sm-9 col-xs-12">
                                                    <input type="email" className="form-control"
                                                        value={this.state.email} onChange={this.changeHandeler} />
                                                </div>
                                            </div>
                                            <div className="form-group">
                                                <label
                                                    className="col-md-2  col-sm-3 col-xs-12 control-label text-muted">Address: </label>
                                                <div className="col-md-10 col-sm-9 col-xs-12">
                                                    <label
                                                        className="col-md-2  col-sm-3 col-xs-12 control-label">Street </label>
                                                    <input type="text" className="form-control"
                                                        value={this.state.address.street} onChange={this.changeHandeler} />
                                                </div>

                                                <div className="col-md-10 col-sm-9 col-xs-12">
                                                    <label
                                                        className="col-md-2  col-sm-3 col-xs-12 control-label">City </label>
                                                    <input type="text" className="form-control"
                                                        value={this.state.address.city} onChange={this.changeHandeler} />
                                                </div>

                                                <div className="col-md-10 col-sm-9 col-xs-12">
                                                    <label
                                                        className="col-md-2  col-sm-3 col-xs-12 control-label">State </label>
                                                    <input type="text" className="form-control"
                                                        value={this.state.address.state} onChange={this.changeHandeler} />
                                                </div>

                                                <div className="col-md-10 col-sm-9 col-xs-12">
                                                    <label
                                                        className="col-md-2  col-sm-3 col-xs-12 control-label">Zip </label>
                                                    <input type="text" className="form-control"
                                                        value={this.state.address.zip} onChange={this.changeHandeler} />
                                                </div>
                                            </div>
                                        </fieldset>
                                        <hr />
                                        <div className="form-group">
                                            <div
                                                className="col-md-10 col-sm-9 col-xs-12 col-md-push-2 col-sm-push-3 col-xs-push-0">
                                                <input className="btn btn-primary" type="button"
                                                    value="Update Profile" onClick={this.handleShow} />
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>


                <Modal show={this.state.setShow} onHide={this.handleClose} animation={false}>
                    <Modal.Header closeButton>
                        <Modal.Title>Create new pool</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <div className="text-muted text-center join-pool-modal-text">
                            Update your Profile
                        </div>
                        <Form>
                            <Form.Group controlId="formGroupItemName">
                                <Form.Label>First Name:</Form.Label>
                                <Form.Control type="text" name="tempFirstName"
                                    value={this.state.tempFirstName} className="join-pool-modal-text" onChange={this.changeHandeler} />

                                <Form.Label>Last Name:</Form.Label>
                                <Form.Control type="text" name="tempLastName"
                                    value={this.state.tempLastName} className="join-pool-modal-text" onChange={this.changeHandeler} />

                                <Form.Label>Email:</Form.Label>
                                <Form.Control type="text" name="tempEmail"
                                    value={this.state.tempEmail} className="join-pool-modal-text" onChange={this.changeHandeler} />

                                <Form.Label className="text-muted">Adddress:</Form.Label><hr />
                                <Form.Label>Street:</Form.Label>
                                <Form.Control type="text" name="tempStreet"
                                    value={this.state.tempStreet} className="join-pool-modal-text" onChange={this.changeHandeler} />

                                <Form.Label>City:</Form.Label>
                                <Form.Control type="text" name="tempCity"
                                    value={this.state.tempCity} className="join-pool-modal-text" onChange={this.changeHandeler} />

                                <Form.Label>State:</Form.Label>
                                <Form.Control type="text" name="tempState"
                                    value={this.state.tempState} className="join-pool-modal-text" onChange={this.changeHandeler} />

                                <Form.Label>Zip:</Form.Label>
                                <Form.Control type="text" name="tempZip"
                                    value={this.state.tempZip} className="join-pool-modal-text" onChange={this.changeHandeler} />
                            </Form.Group>
                        </Form>

                    </Modal.Body>
                    <Modal.Footer>

                        <Button variant="secondary" onClick={this.handleClose}>
                            Close
                        </Button>

                        <Button variant="primary" onClick={this.submitChangeProfile}>
                            Submit
                        </Button>

                    </Modal.Footer>
                </Modal>
            </React.Fragment>
        );
    }
}

export default ProfilePage;