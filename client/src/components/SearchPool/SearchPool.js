import React, { Component } from 'react';
import { HOSTNAME } from "../../constants/appConstants";
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css'
import { Button, Card, Col, Form, InputGroup, Modal, Row, Table } from 'react-bootstrap'
import './SearchPool.css';

class SearchPool extends Component {
    constructor(props) {
        super(props);
        this.state = Object.assign({}, { searchParam: "" }, { pools: [] }, { setShow: false },
            { screenName: "" }, { isCheckboxDisabled: false }, { screenNameTextBoxDisableFlag: false }, { checked: false }, { poolId: null });

    }

    submitSearchHandler = () => {
        let searchParam = this.state.searchParam;
        axios.defaults.withCredential = true;
        axios.get(`http://${HOSTNAME}:8080/pool/search/${searchParam}`)
            .then(response => {
                this.setState({
                    poolList: response.data,
                    searchParam: ""
                }, () => { if (this.state.poolList.length === 0) alert(`No pools found with ${this.state.searchParam} keyword`) })
            })
            .catch(error => {
                console.log(error);
            })
    };

    changeScreenNameTextBoxDisableFlag = () => {
        this.setState({
            screenNameTextBoxDisableFlag: this.state.checked
        })
    }
    changeCheckboxDisabledFlag = () => {
        this.setState({
            isCheckboxDisabled: this.state.screenName !== "" ? true : false
        })
    };

    changeHandeler = (e) => {
        this.setState({
            [e.target.name]: e.target.value,
        }, () => this.changeCheckboxDisabledFlag());
    };

    handleClose = () => {
        this.setState({
            setShow: false,
            screenName: "",
            checked: false
        });
    }

    handleShow = () => {
        this.setState({
            setShow: true
        });
    }

    handleCheck = () => {
        this.setState({
            checked: !this.state.checked
        }, () => this.changeScreenNameTextBoxDisableFlag())
    }

    submitJoinHandler = () => {
        let reference = this.state.checked ? 'pool_leader_reference' : this.state.screenName;
        let poolId = this.state.poolId;
        let poolerId = localStorage.getItem('id');
        //let poolerId = 21;

        let payload = {
            screenName: reference,
            poolerId: poolerId
        }
        axios.put(`http://${HOSTNAME}:8080/pool/join/${poolId}`, null, { params: payload })
            .then(response => {
                if (response.status === 200) {
                    alert(response.data.message);
                    this.handleClose();
                }

            })
            .catch(error => {
                alert(error)
            })
    }

    renderPoolList = (pool) => {
        return (<div key={pool.id}>
            <Row>
                <Col xs={12}>
                    <Card className="search-pool-card">

                        <Card.Header>
                            <Row><Col xs={12} md={8}><h2>{pool.name}</h2></Col>
                                <Col xs={6} md={4}><Button variant="info" onClick={
                                    () => {
                                        this.setState({
                                            poolId: pool.id
                                        },
                                            () => this.handleShow()
                                        )
                                    }
                                }>Join</Button></Col>
                            </Row>
                        </Card.Header>

                        <Card.Body>
                            <Card.Title>Pool Details:</Card.Title>
                            <Table responsive>
                                <tbody>
                                    <tr>
                                        <th>Pool Name</th>
                                        <td>{pool.name}</td>
                                    </tr>
                                    <tr>
                                        <th>NeighborHood Name</th>
                                        <td>{pool.neighborhoodName}</td>
                                    </tr>
                                    <tr>
                                        <th>Zip</th>
                                        <td>{pool.zip}</td>
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
            <div style={styles.container}>
                <div style={styles.message}>
                    <React.Fragment>
                        <div className="container-fluid">
                            <div className="search-pool-title"><h3>Search Pool by name, neighborhood or zip</h3></div>
                            <div><input type="text" className="search-pool-search-bar" name="searchParam"
                                value={this.state.searchParam}
                                onChange={this.changeHandeler}
                                placeholder="Search Pool..." />
                                <Button variant="primary">
                                    <p className="search-pool-btn" size="lg"
                                        onClick={this.submitSearchHandler}>Search</p>
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
                                <div className="text-muted text-center join-pool-modal-text">Either enter reference
                                    pooler's screen name or check the box if you know the pool leader
                                </div>
                                <Form>
                                    <Form.Group controlId="formGroupItemName">
                                        <Form.Label>Enter Screen name of reference Pooler:</Form.Label>
                                        <Form.Control type="text" name="screenName"
                                            disabled={this.state.screenNameTextBoxDisableFlag}
                                            value={this.state.screenName} onChange={this.changeHandeler}
                                            className="join-pool-modal-text" />
                                        <div className="join-pool-modal-text">Or</div>
                                        <InputGroup className="mb-3">
                                            <InputGroup.Prepend>
                                                <InputGroup.Checkbox aria-label="Checkbox for following text input"
                                                    className="search-pool-checkbox"
                                                    disabled={this.state.isCheckboxDisabled}
                                                    defaultChecked={this.state.defaultChecked}
                                                    onChange={this.handleCheck} />
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

                    </React.Fragment>
                </div>
            </div>
        );
    }
}

const styles = {
    container: {
        display: "flex",
        flexDirection: "column",
        alignItems: "center"
    },
    message: {
        paddingTop: "10%"
    },
}

export default SearchPool;