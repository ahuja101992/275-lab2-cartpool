import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import './Profile.css';
import { HOSTNAME } from "../../constants/appConstants";
import axios from 'axios';

class ProfilePage extends Component {
    constructor(props) {
        super(props);
        this.state = Object.assign({}, { firstName: "Vijay" }, { email: "vijayghanshani2@gmail.com" }, { imageUrl: "https://bootdey.com/img/Content/avatar/avatar1.png" },
            { credits: 5 }, { address: "" });

    }

    componentDidMount() {
        let poolerId = localStorage.getItem('id');
        axios.get(`http://${HOSTNAME}:8080/pool/profile/getById/${poolerId}`)
            .then(response => {
                console.log(response);
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
                                        <img className="img-profile img-circle img-responsive center-block" src={this.state.imageUrl} alt="" />
                                        <ul className="meta list list-unstyled">
                                            <li className="name">{this.state.firstName}
                                                <label className="label label-info">{this.state.email}</label>
                                            </li>
                                        </ul>
                                    </div>
                                    <nav className="side-menu">
                                        <ul className="nav">
                                            <li className="active"><a href="#"><span className="fa fa-user"></span> Profile</a></li>
                                            <li><a href="#"><span className="fa fa-cog"></span> Settings</a></li>
                                            <li><a href="#"><span className="fa fa-credit-card"></span> Upcoming Orders</a></li>
                                            <li><a href="#"><span className="fa fa-envelope"></span> Past Orders</a></li>
                                        </ul>
                                    </nav>
                                </div>
                                <div className="content-panel">
                                    <h2 className="title">Credits<span className="pro-label label label-warning">{this.state.credits}</span></h2>
                                    <form className="form-horizontal">
                                        <fieldset className="fieldset">
                                            <h3 className="fieldset-title text-muted">Personal Info</h3>
                                            <div className="form-group avatar">
                                                <figure className="figure col-md-2 col-sm-3 col-xs-12">
                                                    <img className="img-rounded img-responsive" src={this.state.imageUrl} alt="" />
                                                </figure>
                                                <div className="form-inline col-md-10 col-sm-9 col-xs-12">
                                                    <input type="file" className="file-uploader pull-left" />
                                                    <button type="submit" className="btn btn-sm btn-default-alt pull-left">Update Image</button>
                                                </div>
                                            </div>
                                            <div className="form-group">
                                                <label className="col-md-2 col-sm-3 col-xs-12 control-label">User Name</label>
                                                <div className="col-md-10 col-sm-9 col-xs-12">
                                                    <input type="text" className="form-control" value={this.state.firstName} />
                                                </div>
                                            </div>

                                            <div className="form-group">
                                                <label className="col-md-2 col-sm-3 col-xs-12 control-label">First Name</label>
                                                <div className="col-md-10 col-sm-9 col-xs-12">
                                                    <input type="text" className="form-control" value={this.state.firstName} />
                                                </div>
                                            </div>
                                            <div className="form-group">
                                                <label className="col-md-2 col-sm-3 col-xs-12 control-label">Last Name</label>
                                                <div className="col-md-10 col-sm-9 col-xs-12">
                                                    <input type="text" className="form-control" value={this.state.firstName} />
                                                </div>
                                            </div>
                                        </fieldset>
                                        <fieldset className="fieldset">
                                            <h3 className="fieldset-title text-muted">Contact Info</h3>
                                            <div className="form-group">
                                                <label className="col-md-2  col-sm-3 col-xs-12 control-label">Email</label>
                                                <div className="col-md-10 col-sm-9 col-xs-12">
                                                    <input type="email" className="form-control" value={this.state.email} />
                                                    <p className="help-block">This is the email </p>
                                                </div>
                                            </div>
                                            <div className="form-group">
                                                <label className="col-md-2  col-sm-3 col-xs-12 control-label">Address</label>
                                                <div className="col-md-10 col-sm-9 col-xs-12">
                                                    <input type="text" className="form-control" value={this.state.address} />
                                                </div>
                                            </div>
                                        </fieldset>
                                        <hr />
                                        <div className="form-group">
                                            <div className="col-md-10 col-sm-9 col-xs-12 col-md-push-2 col-sm-push-3 col-xs-push-0">
                                                <input className="btn btn-primary" type="submit" value="Update Profile" />
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>

            </React.Fragment >
        );
    }
}

export default ProfilePage;