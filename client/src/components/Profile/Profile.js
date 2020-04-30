import React, { Component } from 'react';
import { Button, Modal, Form } from 'react-bootstrap'
import { HOSTNAME } from "../../constants/appConstants";
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css'

class Profile extends Component {
    constructor(props) {
        super(props);
        this.state = Object.assign({}, { setShow: false }, { poolName: "" }, { poolNickname: "" }, { zip: "" });

    }

    changeHandeler = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        })
    };

    handleClose = () => {
        this.setState({
            setShow: false,
            poolName: "",
            poolNickname: "",
            zip: ""
        });
    }

    handleShow = () => {
        this.setState({
            setShow: true
        });
    }
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
                                <Form.Label>Pool Name:</Form.Label>
                                <Form.Control type="text" name="poolName"
                                    value={this.state.poolName} onChange={this.changeHandeler}
                                    className="join-pool-modal-text" />

                                <Form.Label>Pool Nickname:</Form.Label>
                                <Form.Control type="text" name="poolNickname"
                                    value={this.state.poolNickname} onChange={this.changeHandeler}
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

                        <Button variant="primary" onClick={this.submitJoinHandler}>
                            Submit
                                </Button>

                    </Modal.Footer>
                </Modal>
            </React.Fragment>
        );
    }
}

export default Profile;