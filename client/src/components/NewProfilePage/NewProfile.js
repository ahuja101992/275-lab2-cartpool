import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import './Profile.css';
import { HOSTNAME } from '../../constants/appConstants';
import { Button, Form, Modal, Row } from 'react-bootstrap'
import axios from 'axios';
import * as ArrayComponnet from './Array/indexArray';

class NewProfile extends Component {
    constructor(props) {
        super(props);
        this.state = Object.assign({}, { firstName: "" }, { lastName: "" }, { email: "" }, { imageUrl: "https://bootdey.com/img/Content/avatar/avatar1.png" },
            { credits: 0 }, { screenName: "" }, { address: { city: "", state: "", street: "", zip: "" } }, { selectedComponent: "" }, { display: false },
            { pool: {} });

        this.renderSelectedComponent = this.renderSelectedComponent.bind(this);
    }

    componentDidMount() {
        //let poolerId = localStorage.getItem('id');
        let _pool = {
            id: "",
            poolId: "",
            name: "",
            neighborhoodName: "",
            description: "",
            zip: ""
        }
        let poolerId = 20;
        axios.get(`http://${HOSTNAME}:8080/pooler/profile/getById/${poolerId}`)
            .then(response => {
                console.log(response.data.pool);
                this.setState({
                    firstName: response.data.firstName,
                    lastName: response.data.lastName,
                    email: response.data.email,
                    address: response.data.address ? { ...response.data.address } : "",
                    imageUrl: response.data.img ? response.data.img : "https://bootdey.com/img/Content/avatar/avatar1.png",
                    screenName: response.data.screenname,
                    credits: response.data.contribution,
                    pool: response.data.pool ? response.data.pool : _pool,
                    display: true

                });
                this.renderSelectedComponent(this.state.selectedComponent)
            })
            .catch(error => {
                console.log(error);
            })
    }

    renderSelectedComponent(selectedComponent) {
        let C = ArrayComponnet['Profile'];
        let info = {
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            email: this.state.email,
            imageUrl: this.state.imageUrl,
            street: this.state.address.street,
            city: this.state.address.city,
            state: this.state.address.state,
            zip: this.state.address.zip,
            credits: this.state.credits,
            screenName: this.state.screenName,
            pool: this.state.pool
        }
        if (!selectedComponent) {
            return <C
                {...info}
                submitChanges={this.submitChanges}
            />;
        }

        C = ArrayComponnet[selectedComponent];
        return <C
            {...info}
            submitChanges={this.submitChanges}
        />;
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
                                            </li>
                                        </ul>
                                    </div>
                                    <nav className="side-menu">
                                        <ul className="nav">
                                            <li className="active"><a href="#" name="Profile" onClick={e => {
                                                e.preventDefault();
                                                this.setState({ selectedComponent: e.target.name })
                                            }}>
                                                <span className="fa fa-user"></span> Profile</a>
                                            </li>

                                            <li><a href="#" name="PoolInfo" onClick={e => {
                                                e.preventDefault();
                                                this.setState({ selectedComponent: e.target.name })
                                            }}><span className="fa fa-cog"></span> Pool Info</a>
                                            </li>
                                            <li><a href="#"><span className="fa fa-credit-card"></span> Upcoming Orders</a>
                                            </li>
                                            <li><a href="#"><span className="fa fa-envelope"></span> Past Orders</a>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>

                                <div className="content-panel">
                                    <div >
                                        {
                                            this.state.display ?
                                                this.renderSelectedComponent(this.state.selectedComponent)
                                                : null
                                        }

                                    </div>
                                </div>

                            </div>
                        </section>
                    </div>
                </div>

            </React.Fragment>
        );
    }
}

export default NewProfile;