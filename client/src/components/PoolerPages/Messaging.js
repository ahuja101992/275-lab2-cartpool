import React, {Component} from "react";
import {connect} from "react-redux";
import {Badge, Button, Card, Col, Form, ListGroup, Modal} from "react-bootstrap";
import BootstrapTable from "react-bootstrap-table-next";
import {sendMessage, getAllPollers} from "../../redux/actions/messagingActions";
import {DELIVERED} from "../../constants/appConstants";

function mapStateToProps(store) {
    return {
        allPoolers: store.messaging.allPoolers,
    }
}

function mapDispatchToProps(dispatch) {
    return {
        getAllPollers: (payload) => dispatch(getAllPollers(payload)),
        sendMessage: (payload) => dispatch(sendMessage(payload)),

    };
}

class Messaging extends Component {
    constructor(props) {
        super(props);
        this.state = {
            redirectVar: null,
            selectedOrder: null,
            currentStoreEditIndex: null,
            show: false,
            poolers: ['ram', 'shyam', 'sita'],
            currentPooler: null,
            showEmailBox: false
        };
    }

    componentDidMount() {
        this.props.getAllPollers();
    }

    userClicked = (pooler) => {
        console.log("In alertClicked: " + pooler)
        this.setState({showEmailBox: true, currentPooler: pooler})
    }

    handleClose = () => this.setState({showEmailBox: false});

    sendMessage = (e) => {
        e.preventDefault();

        console.log("Inside sendMessage")

        const data = {};
        for (let i = 0; i < e.target.length; i++) {
            if (e.target[i].id !== "") {
                data[e.target[i].id] = e.target[i].value;
            }
        }

        let updatedData = {
            message: data.message,
            to: this.state.currentPooler.email,
            from: "xxx",
        }

        this.props.sendMessage(updatedData);
        this.handleClose();
    }

    render() {
        const renderTodos = this.props.allPoolers.map((pooler, index) => {
            console.log("pooler")
            console.log(pooler)

            return <ListGroup.Item style={{margin: "1px", width: "10rem"}} action onClick={() => this.userClicked(pooler)}>
                {pooler.screenname}
            </ListGroup.Item>
        });

        return (
            <div>
                <h1>Messaging</h1>
                <div className='rowC'>
                    <ListGroup>
                        {renderTodos}
                    </ListGroup>

                    {this.state.showEmailBox && <Form style={styles.emailBox} onSubmit={this.sendMessage}>
                        <Form.Group controlId="name" style={styles.formField}>
                            <Form.Label>Name</Form.Label>
                            <Form.Control
                                disabled={true}
                                defaultValue={this.state.currentPooler.screenname !== null ? this.state.currentPooler.screenname : ""}/>
                        </Form.Group>

                        <Form.Group controlId="message" style={styles.formField}>
                            <Form.Label>Message</Form.Label>
                            <Form.Control
                                as="textarea" rows="3"
                                placeholder="Enter your message" required/>
                        </Form.Group>

                        <Modal.Footer>
                            <Button variant="secondary" onClick={this.handleClose}>
                                Cancel
                            </Button>
                            <Button variant="primary" type="submit">
                                Send
                            </Button>
                        </Modal.Footer>
                    </Form>}
                </div>
            </div>
        );
    }
}

const styles = {
    container: {
        flex: 1,
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'stretch',
    },
    button: {
        marginLeft: "45%"
    },
    emailBox: {
      marginLeft: "5rem",
        width: "30%"
    },
    storeList: {
        flex: 2,
        alignSelf: 'left'
    },
    formField: {
        marginLeft: "1rem",
        marginRight: "1rem"
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Messaging);