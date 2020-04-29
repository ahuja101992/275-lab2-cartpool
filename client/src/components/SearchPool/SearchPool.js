import React, { Component } from 'react';
import { HOSTNAME } from "../../constants/appConstants";
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css'
import { Button, Card, Table, Row, Col, Modal, Form, InputGroup, FormControl } from 'react-bootstrap'
import './SearchPool.css';
import ModalManager from 'react-overlays/esm/ModalManager';

class SearchPool extends Component {
    constructor(props) {
        super(props);
        this.state = Object.assign({}, { searchParam: "" }, { pools: [] }, { setShow: false }, { screenName: "" });
    }

    submitSearchHandler = () => {
        let searchParam = this.state.searchParam;
        axios.defaults.withCredential = true;
        axios.get(`http://${HOSTNAME}:8080/pool/search/${searchParam}`)
            .then(response => {
                console.log(response.data);
                this.setState({
                    poolList: response.data,
                    searchParam: ""
                })
            })
            .catch(error => {
                console.log(error);
            })
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

    submitJoinHandler = () => {
        console.log('submitted join request')
    }

    renderPoolList = (pool) => {
        console.log('in render function for pools')
        return (<div key={pool.id}>
            <Row>
                <Col xs={12}>
                    <Card className="search-pool-card">

                        <Card.Header>
                            <Row><Col xs={12} md={8}><h2>{pool.name}</h2></Col>
                                <Col xs={6} md={4}><Button variant="info" onClick={this.handleShow}>Join</Button></Col>
                            </Row>
                        </Card.Header>

                        <Card.Body>
                            <Card.Title>Pool Details:</Card.Title>
                            <Table responsive>
                                <tbody>
                                    <tr>
                                        <th>Pool Name</th><td>{pool.name}</td>
                                    </tr>
                                    <tr>
                                        <th>NeighborHood Name</th><td>{pool.neighborhoodName}</td>
                                    </tr>
                                    <tr>
                                        <th>Zip</th><td>{pool.zip}</td>
                                    </tr>
                                </tbody>
                            </Table>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </div>)
    }

    render() {
        let poolList = this.state.poolList ? this.state.poolList : []
        console.log(poolList);
        return (
            <React.Fragment>
                <div className="container-fluid">
                    <div className="search-pool-title"><h3>Search Pool by name, neighborhood or zip</h3></div>
                    <div><input type="text" className="search-pool-search-bar" name="searchParam" value={this.state.searchParam}
                        onChange={this.changeHandeler}
                        placeholder="Search Pool..." />
                        <Button variant="primary" >
                            <p className="search-pool-btn" size="lg" onClick={this.submitSearchHandler}>Search</p>
                        </Button>
                    </div>
                    <div className="cards-container">
                        {
                            poolList.map((pool, index) => {
                                return (this.renderPoolList(pool))
                            })
                        }
                    </div>

                </div>


                <Modal show={this.state.setShow} onHide={this.handleClose} animation={false}>
                    <Modal.Header closeButton>
                        <Modal.Title>Join Pool</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <div className="text-muted text-center join-pool-modal-text">Either enter reference pooler's screen name or check the box if you know the pool leader</div>
                        <Form>
                            <Form.Group controlId="formGroupItemName">
                                <Form.Label>Enter Screen name of reference Pooler:</Form.Label>
                                <Form.Control type="text" name="screenName"
                                    value={this.state.screenName} onChange={this.changeHandeler} className="join-pool-modal-text" />
                                <div className="join-pool-modal-text">Or</div>
                                <InputGroup className="mb-3">
                                    <InputGroup.Prepend>
                                        <InputGroup.Checkbox aria-label="Checkbox for following text input" className="search-pool-checkbox" />
                                        <span className="search-pool-checkbox-text">If you know the pool leader, check the box here</span>
                                    </InputGroup.Prepend>
                                </InputGroup>
                                {/* If you know the pool leader, check the box here. */}
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

            </React.Fragment >
        );
    }
}

export default SearchPool;