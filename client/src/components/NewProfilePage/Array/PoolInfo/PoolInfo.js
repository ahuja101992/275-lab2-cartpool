import React, { Component } from 'react';
import { Button, Form, Modal, Table } from 'react-bootstrap'
import { HOSTNAME } from "../../../../constants/appConstants";
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css'

class PoolInfo extends Component {
    constructor(props) {
        super(props);
        console.log(this.props);
        this.state = Object.assign({}, { setShow: false }, { id: this.props.pool.id }, { poolId: this.props.pool.poolId }, { zip: this.props.pool.zip },
            { poolName: this.props.pool.name }, { poolNeighborhoodName: this.props.pool.neighborhoodName }, { poolDescription: this.props.pool.description },
            { tempPoolName: this.props.pool.name },
            { tempPoolNeighborhoodName: this.props.pool.neighborhoodName }, { tempPoolDescription: this.props.pool.description },
            { isPoolLeader: false }, { setShowUpdateModal: false });

    }

    componentDidMount() {
        //let poolerId = 20;
        let poolId = this.state.id;
        //let poolId = 14;
        //console.log(this.state.pool);
        let poolerId = localStorage.getItem('id');
        if (this.state.id && this.state.id !== "") {
            axios.get(`http://${HOSTNAME}:8080/pool/getLeader/${poolId}`)
                .then(response => {
                    console.log(response.data);
                    this.setState({
                        isPoolLeader: response.data == poolerId
                    })

                })
                .catch(error => {
                    console.log(error);
                })
        }
    }

    changeHandeler = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        })
    };

    handleClose = () => {
        let obj = {
            poolId: "",
            poolName: "",
            poolNeighborhoodName: "",
            poolDescription: "",
            zip: ""
        }
        this.setState({
            setShow: false,
            setShowUpdateModal: false,
            pool: obj
        });
    }

    handleShow = () => {
        //console.log('aaya');
        this.setState({
            setShow: true
        });
    }
    setShowUpdateModal = () => {
        this.setState({
            setShowUpdateModal: true
        });
    }
    submitCreatePoolHandler = () => {
        let payload = {
            poolId: this.state.poolId,
            name: this.state.poolName,
            neighborhoodName: this.state.poolNeighborhoodName,
            description: this.state.poolDescription,
            zip: this.state.zip,
            poolerId: localStorage.getItem('id')
            //poolerId: 20
        }

        axios.post(`http://${HOSTNAME}:8080/pool/create`, null, { params: payload })
            .then(response => {
                alert(response.data.message);
                this.setState({
                    id: response.data.id,
                    isPoolLeader: true
                }, () => this.handleClose())
            })
            .catch(error => {
                alert(error.response.data.message);
            })
    };

    handleDelete = () => {
        let poolId = this.state.id;

        axios.delete(`http://${HOSTNAME}:8080/pool/delete/${poolId}`)
            .then(response => {
                this.setState({
                    id: "",
                    isPoolLeader: false,
                    poolId: "",
                    zip: "",
                    poolName: "",
                    poolNeighborhoodName: "",
                    poolDescription: ""
                }, () => alert(response.data))
            })
            .catch(error => {
                console.log(error);
            })
    }

    submitUpdatePoolHandler = () => {
        console.log('inside pool handleUpdate')
        let poolId = this.state.id;
        let payload = {
            name: this.state.tempPoolName,
            neighborhoodName: this.state.tempPoolNeighborhoodName,
            description: this.state.tempPoolDescription
        }
        axios.put(`http://${HOSTNAME}:8080/pool/update/${poolId}`, null, { params: payload })
            .then(response => {
                console.log(response.data);
                this.setState({
                    poolName: this.state.tempPoolName,
                    poolNeighborhoodName: this.state.tempPoolNeighborhoodName,
                    poolDescription: this.state.tempPoolDescription,
                }, () => this.handleClose())

            })
            .catch(error => {
                alert(error.response.data.message);
            })
    }
    renderButtons = () => {
        return (
            <React.Fragment>

                <Button variant="primary" size="lg"
                    className="profile-btn"
                    onClick={this.setShowUpdateModal}>Update Pool
                </Button>

                <Button variant="danger" size="lg"
                    className="profile-btn"
                    onClick={this.handleDelete}>Delete Pool
                </Button>
            </React.Fragment>
        )
    }

    renderPoolDetails = () => {
        if (this.state.id && this.state.id !== "") {
            return (<React.Fragment>
                <Table responsive>
                    <tbody>
                        <tr>
                            <th>Pool Id</th>
                            <td>{this.state.poolId}</td>
                        </tr>
                        <tr>
                            <th>Pool Name</th>
                            <td>{this.state.poolName}</td>
                        </tr>
                        <tr>
                            <th>NeighborHood Name</th>
                            <td>{this.state.poolNeighborhoodName}</td>
                        </tr>
                        <tr>
                            <th>Description</th>
                            <td>{this.state.poolDescription}</td>
                        </tr>
                        <tr>
                            <th>Zip</th>
                            <td>{this.state.zip}</td>
                        </tr>
                    </tbody>
                </Table>
            </React.Fragment>);
        } else {
            return (
                <React.Fragment>
                    <h2>create new pool</h2>
                    <Button variant="success" size="lg"
                        className="profile-btn"
                        onClick={this.handleShow}>Create Pool
                    </Button>
                </React.Fragment>
            )
        }
    }

    render() {
        return (
            <React.Fragment>
                {
                    this.renderPoolDetails()
                }

                {
                    this.state.isPoolLeader ?
                        this.renderButtons()
                        : null
                }

                {/* //////////////        CREATE POOL MODAL      //////////////*/}
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
                                    className="join-pool-modal-text" required />

                                <Form.Label>Pool Name:</Form.Label>
                                <Form.Control type="text" name="poolName"
                                    value={this.state.poolName} onChange={this.changeHandeler}
                                    className="join-pool-modal-text" required />

                                <Form.Label>Pool NeighborhoodName:</Form.Label>
                                <Form.Control type="text" name="poolNeighborhoodName"
                                    value={this.state.poolNeighborhoodName} onChange={this.changeHandeler}
                                    className="join-pool-modal-text" required />

                                <Form.Label>Pool Description:</Form.Label>
                                <Form.Control type="text" name="poolDescription"
                                    value={this.state.poolDescription} onChange={this.changeHandeler}
                                    className="join-pool-modal-text" required />

                                <Form.Label>ZipCode:</Form.Label>
                                <Form.Control type="text" name="zip"
                                    value={this.state.zip} onChange={this.changeHandeler}
                                    className="join-pool-modal-text" required />
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


                {/* //////////////        UPDATE POOL MODAL      //////////////*/}
                <Modal show={this.state.setShowUpdateModal} onHide={this.handleClose} animation={false}>
                    <Modal.Header closeButton>
                        <Modal.Title>Update Pool</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form>
                            <Form.Group controlId="formGroupItemName">
                                <Form.Label>Pool Name:</Form.Label>
                                <Form.Control type="text" name="tempPoolName"
                                    value={this.state.tempPoolName} onChange={this.changeHandeler}
                                    className="join-pool-modal-text" />

                                <Form.Label>Pool NeighborhoodName:</Form.Label>
                                <Form.Control type="text" name="tempPoolNeighborhoodName"
                                    value={this.state.tempPoolNeighborhoodName} onChange={this.changeHandeler}
                                    className="join-pool-modal-text" />

                                <Form.Label>Pool Description:</Form.Label>
                                <Form.Control type="text" name="tempPoolDescription"
                                    value={this.state.tempPoolDescription} onChange={this.changeHandeler}
                                    className="join-pool-modal-text" />
                            </Form.Group>

                        </Form>

                    </Modal.Body>
                    <Modal.Footer>

                        <Button variant="secondary" onClick={this.handleClose}>
                            Close
                        </Button>

                        <Button variant="primary" onClick={this.submitUpdatePoolHandler}>
                            Submit
                        </Button>

                    </Modal.Footer>
                </Modal>
            </React.Fragment>
        );
    }
}

export default PoolInfo;