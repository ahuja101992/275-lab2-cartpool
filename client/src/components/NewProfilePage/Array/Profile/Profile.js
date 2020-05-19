import React, {Component} from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../../Profile.css';
import {HOSTNAME} from '../../../../constants/appConstants'
import {Button, Form, Modal} from 'react-bootstrap'
import axios from 'axios';

class Profile extends Component {
    constructor(props) {
        super(props);
        this.state = Object.assign({}, {firstName: this.props.firstName}, {lastName: this.props.lastName}, {email: this.props.email}, {imageUrl: this.props.imageUrl},
            {street: this.props.street}, {city: this.props.city}, {state: this.props.state}, {zip: this.props.zip}, {screenName: this.props.screenName},
            {credits: this.props.credits}, {setShow: false}, {tempFirstName: this.props.firstName}, {tempLastName: this.props.lastName}, {tempEmail: this.props.email},
            {tempStreet: this.props.street}, {tempCity: this.props.city}, {tempState: this.props.state}, {tempZip: this.props.zip},
            {temp_imageUrl: this.props.imageUrl});

        //this.onFileChange = this.onFileChange.bind(this);
    }


    getCotributionStyle = () => {
        let style = "cotributionBadge badge badge-";
        const contributionCount = this.state.credits;
        if (contributionCount > -4) style += "success";
        else if (contributionCount <= -4 && contributionCount > -6) style += "warning";
        else style += "danger";
        return style;
    };
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

    submitChangeProfile = () => {
        let poolerId = localStorage.getItem('id');
        //let poolerId = 3;
        let payload = {
            firstName: this.state.tempFirstName,
            lastName: this.state.tempLastName,
            email: this.state.tempEmail,
            imageUrl: this.props.imageUrl,
            street: this.state.tempStreet,
            city: this.state.tempCity,
            state: this.state.tempState,
            zip: this.state.tempZip
        }
        axios.put(`http://${HOSTNAME}:8080/pooler/update/${poolerId}`, null, {params: payload})
            .then(response => {
                this.setState({
                    firstName: this.state.tempFirstName,
                    lastName: this.state.tempLastName,
                    email: this.state.tempEmail,
                    imageUrl: this.props.imageUrl,
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
        console.log(this.props.imageUrl)
        return (
            <React.Fragment>
                <h2 className="title">Credits<span
                    className={this.getCotributionStyle()}>{this.state.credits}</span></h2>
                <form className="form-horizontal">
                    <fieldset className="fieldset">
                        <h3 className="fieldset-title text-muted">Personal Info</h3>

                        <div className="form-group">
                            <label className="col-md-2 col-sm-3 col-xs-12 control-label">Screen
                                Name</label>
                            <div className="col-md-10 col-sm-9 col-xs-12">
                                <input type="text" className="form-control"
                                       value={this.state.screenName} onChange={this.changeHandeler}/>
                            </div>
                        </div>

                        <div className="form-group">
                            <label className="col-md-2 col-sm-3 col-xs-12 control-label">First
                                Name</label>
                            <div className="col-md-10 col-sm-9 col-xs-12">
                                <input type="text" className="form-control"
                                       value={this.state.firstName} onChange={this.changeHandeler}/>
                            </div>
                        </div>
                        <div className="form-group">
                            <label className="col-md-2 col-sm-3 col-xs-12 control-label">Last
                                Name</label>
                            <div className="col-md-10 col-sm-9 col-xs-12">
                                <input type="text" className="form-control"
                                       value={this.state.lastName} onChange={this.changeHandeler}/>
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
                                       value={this.state.email} onChange={this.changeHandeler}/>
                            </div>
                        </div>
                        <div className="form-group">
                            <label
                                className="col-md-2  col-sm-3 col-xs-12 control-label text-muted">Address: </label>
                            <div className="col-md-10 col-sm-9 col-xs-12">
                                <label
                                    className="col-md-2  col-sm-3 col-xs-12 control-label">Street </label>
                                <input type="text" className="form-control"
                                       value={this.state.street} onChange={this.changeHandeler}/>
                            </div>

                            <div className="col-md-10 col-sm-9 col-xs-12">
                                <label
                                    className="col-md-2  col-sm-3 col-xs-12 control-label">City </label>
                                <input type="text" className="form-control"
                                       value={this.state.city} onChange={this.changeHandeler}/>
                            </div>

                            <div className="col-md-10 col-sm-9 col-xs-12">
                                <label
                                    className="col-md-2  col-sm-3 col-xs-12 control-label">State </label>
                                <input type="text" className="form-control"
                                       value={this.state.state} onChange={this.changeHandeler}/>
                            </div>

                            <div className="col-md-10 col-sm-9 col-xs-12">
                                <label
                                    className="col-md-2  col-sm-3 col-xs-12 control-label">Zip </label>
                                <input type="text" className="form-control"
                                       value={this.state.zip} onChange={this.changeHandeler}/>
                            </div>
                        </div>
                    </fieldset>
                    <hr/>
                    <div className="form-group">
                        <div
                            className="col-md-10 col-sm-9 col-xs-12 col-md-push-2 col-sm-push-3 col-xs-push-0">
                            <input className="btn btn-primary" type="button"
                                   value="Update Profile" onClick={this.handleShow}/>
                        </div>
                    </div>
                </form>

                <Modal show={this.state.setShow} onHide={this.handleClose} animation={false}>
                    <Modal.Header closeButton>
                        <Modal.Title>Create new pool</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <div className="text-muted text-center join-pool-modal-text">
                            Update your Profile
                        </div>
                        <Form>
                            <div className="form-group avatar">
                                <figure className="figure col-md-2 col-sm-3 col-xs-12">
                                    <img className="modal-img-profile img-circle img-responsive center-block"
                                         src={this.props.imageUrl} alt=""/>
                                </figure>
                                <div className="form-inline col-md-10 col-sm-9 col-xs-12">
                                    <input type="file" className="file-uploader pull-left" onChange={
                                        (e) => this.props.onFileChange(e.target.files)
                                    }/>
                                </div>
                            </div>
                            <Form.Group controlId="formGroupItemName">
                                <Form.Label>First Name:</Form.Label>
                                <Form.Control type="text" name="tempFirstName"
                                              value={this.state.tempFirstName} className="join-pool-modal-text"
                                              onChange={this.changeHandeler}/>

                                <Form.Label>Last Name:</Form.Label>
                                <Form.Control type="text" name="tempLastName"
                                              value={this.state.tempLastName} className="join-pool-modal-text"
                                              onChange={this.changeHandeler}/>

                                <Form.Label className="text-muted">Adddress:</Form.Label>
                                <hr/>
                                <Form.Label>Street:</Form.Label>
                                <Form.Control type="text" name="tempStreet"
                                              value={this.state.tempStreet} className="join-pool-modal-text"
                                              onChange={this.changeHandeler}/>

                                <Form.Label>City:</Form.Label>
                                <Form.Control type="text" name="tempCity"
                                              value={this.state.tempCity} className="join-pool-modal-text"
                                              onChange={this.changeHandeler}/>

                                <Form.Label>State:</Form.Label>
                                <Form.Control type="text" name="tempState"
                                              value={this.state.tempState} className="join-pool-modal-text"
                                              onChange={this.changeHandeler}/>

                                <Form.Label>Zip:</Form.Label>
                                <Form.Control type="text" name="tempZip"
                                              value={this.state.tempZip} className="join-pool-modal-text"
                                              onChange={this.changeHandeler}/>
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

export default Profile;