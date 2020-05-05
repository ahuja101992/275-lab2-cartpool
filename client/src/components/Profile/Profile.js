import React, { Component } from 'react';
import { Button, Form, Modal } from 'react-bootstrap'
import { HOSTNAME } from "../../constants/appConstants";
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css'

class Profile extends Component {
    constructor(props) {
        super(props);
        this.state = Object.assign({}, { setShow: false }, { poolId: "" }, { poolName: "" }, { poolNeighborhoodName: "" }, { zip: "" }, { poolDescription: "" });

    }

    changeHandeler = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        })
    };

    handleClose = () => {
        this.setState({
            setShow: false,
            poolId: "",
            poolName: "",
            poolNeighborhoodName: "",
            poolDescription: "",
            zip: ""
        });
    }

    handleShow = () => {
        this.setState({
            setShow: true
        });
    }

    submitCreatePoolHandler = () => {
        let payload = {
            poolId: this.state.poolId,
            name: this.state.poolName,
            neighborhoodName: this.state.poolNeighborhoodName,
            description: this.state.poolDescription,
            zip: this.state.zip,
            //poolerId: localStorage.getItem('id')
            poolerId: 20
        }

        axios.post(`http://${HOSTNAME}:8080/pool/create`, null, { params: payload })
            .then(response => {
                alert(response.data.message);
                this.handleClose();
            })
            .catch(error => {
                console.log(error);
            })
    };

    render() {
        return (
            <React.Fragment>
                <Button variant="success" size="lg"
                    className="profile-btn"
                    onClick={this.handleShow}>Create Pool
                </Button>


                <Modal show={this.state.setShow} onHide={this.handleClose} animation={false}>
                    <Modal.Header closeButton>
                        <Modal.Title>Create new pool</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <div className="text-muted text-center join-pool-modal-text">
                            You should not be a member of any other pool to create a new pool!
                        </div>
                        <Form>
                            <Form.Group controlId="formGroupItemName">
                                <Form.Label>Pool Id:</Form.Label>
                                <Form.Control type="text" name="poolId"
                                    value={this.state.poolId} onChange={this.changeHandeler}
                                    className="join-pool-modal-text" />

                                <Form.Label>Pool Name:</Form.Label>
                                <Form.Control type="text" name="poolName"
                                    value={this.state.poolName} onChange={this.changeHandeler}
                                    className="join-pool-modal-text" />

                                <Form.Label>Pool NeighborhoodName:</Form.Label>
                                <Form.Control type="text" name="poolNeighborhoodName"
                                    value={this.state.poolNeighborhoodName} onChange={this.changeHandeler}
                                    className="join-pool-modal-text" />

                                <Form.Label>Pool Description:</Form.Label>
                                <Form.Control type="text" name="poolDescription"
                                    value={this.state.poolDescription} onChange={this.changeHandeler}
                                    className="join-pool-modal-text" />

                                <Form.Label>ZipCode:</Form.Label>
                                <Form.Control type="text" name="zip"
                                    value={this.state.zip} onChange={this.changeHandeler}
                                    className="join-pool-modal-text" />
                            </Form.Group>

                        </Form>

                    </Modal.Body>
                    <Modal.Footer>

                        <Button variant="secondary" onClick={this.handleClose}>
                            Close
                        </Button>

                        <Button variant="primary" onClick={this.submitCreatePoolHandler}>
                            Submit
                        </Button>

                    </Modal.Footer>
                </Modal>
            </React.Fragment>
        );
    }
}

export default Profile;