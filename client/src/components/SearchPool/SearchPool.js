import React, { Component } from 'react';
import { HOSTNAME } from "../../constants/appConstants";
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.css'
import { Button, Card, Table, Row, Col } from 'react-bootstrap'
import './SearchPool.css';

class SearchPool extends Component {
    constructor(props) {
        super(props);
        this.state = Object.assign({}, { searchParam: "" }, { pools: [] });
    }

    submitHandler = () => {
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
            searchParam: e.target.value
        });
    };

    renderPoolList = (pool) => {
        console.log('in render function for pools')
        return (<div key={pool.id}>
            <Row>
                <Col xs={12}>
                    <Card className="search-pool-card">

                        <Card.Header>
                            <Row><Col xs={12} md={8}><h2>{pool.name}</h2></Col>
                                <Col xs={6} md={4}><Button variant="info">Join</Button></Col>
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
                    <div><input type="text" className="search-pool-search-bar" value={this.state.searchParam}
                        onChange={this.changeHandeler}
                        placeholder="Search Pool..." />
                        <Button variant="primary" >
                            <p className="search-pool-btn" size="lg" onClick={this.submitHandler}>Search</p>
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

            </React.Fragment>
        );
    }
}

export default SearchPool;