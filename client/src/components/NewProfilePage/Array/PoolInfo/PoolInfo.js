import React, { Component } from 'react';
import { Button, Form, Modal, Table } from 'react-bootstrap'
import { HOSTNAME } from "../../../../constants/appConstants";
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css'

class PoolInfo extends Component {
    constructor(props) {
        super(props);
        this.state = Object.assign({}, { setShow: false }, { pool: this.props.pool },
            { isPoolLeader: null });

    }

    componentDidMount() {
        let poolerId = 3;

        console.log(this.state.pool);
        //let poolerId = localStorage.getItem('id');
        axios.get(`http://${HOSTNAME}:8080/pool/getLeader/${this.state.pool.id}`)
            .then(response => {
                console.log(response.data);
                this.setState({
                    isPoolLeader: response.data === poolerId
                })

            })
            .catch(error => {
                console.log(error);
            })
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

    handleDelete = () => {
        console.log('inside handleDelete')
    }

    render() {
        return (
            <React.Fragment>
                <Table responsive>
                    <tbody>
                        <tr>
                            <th>Pool Id</th>
                            <td>{this.state.pool.poolId}</td>
                        </tr>
                        <tr>
                            <th>Pool Name</th>
                            <td>{this.state.pool.name}</td>
                        </tr>
                        <tr>
                            <th>NeighborHood Name</th>
                            <td>{this.state.pool.neighborhoodName}</td>
                        </tr>
                        <tr>
                            <th>Zip</th>
                            <td>{this.state.pool.zip}</td>
                        </tr>
                    </tbody>
                </Table>
                <Button variant="success" size="lg"
                    className="profile-btn"
                    onClick={this.handleShow}>Create Pool
                </Button>

                <Button variant="danger" size="lg"
                    className="profile-btn"
                    onClick={this.handleDelete}>Delete Pool
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

export default PoolInfo;