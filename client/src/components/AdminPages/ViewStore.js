import React, {Component} from "react";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.css";
import {Button, Card, Col, Row} from "react-bootstrap";
import "./SearchPool.css";
import {Link} from "react-router-dom";
import {HOSTNAME} from "../../constants/appConstants";

class ViewStore extends Component {
    constructor(props) {
        super(props);
        this.state = Object.assign(
            {},
            {searchParam: ""},
            {pools: []},
            {setShow: false},
            {screenName: ""},
            {isCheckboxDisabled: false},
            {screenNameTextBoxDisableFlag: false},
            {checked: false},
            {poolId: null}
        );
    }

    componentWillMount = () => {
        let searchParam = this.state.searchParam;
        axios.defaults.withCredential = true;
        if (localStorage.getItem("type") === "pooler") {
            axios.defaults.withCredential = true;
            axios
                .get(`http://${HOSTNAME}:8080/inventory/store/allstores`)
                .then((response) => {
                    console.log("stores", response);
                    this.setState({
                        poolList: response.data,
                        searchParam: "",
                    });
                })
                .catch((error) => {
                    console.log(error);
                });
        } else {
            let adminId = localStorage.getItem("id");
            axios.defaults.withCredential = true;
            axios
                .get(`http://${HOSTNAME}:8080/inventory/store/getByAdmin/${adminId}`)
                .then((response) => {
                    console.log("stores", response);
                    this.setState({
                        poolList: response.data,
                        searchParam: "",
                    });
                })
                .catch((error) => {
                    console.log(error);
                });
        }
    };

    renderPoolList = (pool) => {
        let link;
        let linkAdmin = (
            <Link
                to={{
                    pathname: "/homeAdmin/item/",
                    state: {store: pool, id: pool.id, name: pool.name},
                }}
            >
                <Button variant="outline-danger">Select</Button>
            </Link>
        );
        let linkPooler = (
            <Link
                to={{
                    pathname: "/homePooler/item/",
                    state: {store: pool, id: pool.id, name: pool.name},
                }}
            >
                <Button variant="outline-danger">Select</Button>
            </Link>
        );

        link = localStorage.getItem("type") === "pooler" ? linkPooler : linkAdmin;
        return (
            <div key={pool.id}>
                <Row>
                    <Col xs={12}>
                        <Card className="search-pool-card">
                            <Card.Header>
                                <Row>
                                    <Col xs={12} md={8}>
                                        <h2>{pool.name}</h2>
                                    </Col>
                                    <Col xs={6} md={4}>
                                        {link}
                                    </Col>
                                </Row>
                                <Card.Body>
                                    <Card.Subtitle>
                                        <div><h5>Store Address</h5></div>
                                        {pool.address.street}, {pool.address.city},
                                        {pool.address.state} {pool.address.zip}
                                    </Card.Subtitle>
                                </Card.Body>
                            </Card.Header>
                        </Card>
                    </Col>
                </Row>
            </div>
        );
    };

    render() {
        let poolList = this.state.poolList ? this.state.poolList : [];
        console.log(poolList);
        return (
            <div style={styles.container}>
                <div style={styles.message}>
                    <React.Fragment>
                        <div className="container-fluid">
                            <div className="cards-container">
                                {poolList.map((pool, index) => {
                                    return this.renderPoolList(pool);
                                })}
                            </div>
                        </div>
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
        alignItems: "center",
    },
    message: {
        paddingTop: "10%",
    },
};

export default ViewStore;
