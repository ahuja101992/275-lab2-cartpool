import {CHECKOUT, DELIVERY_NOT_RECEIVED, GET_PLACED_ORDERS} from "../../redux/constants/actionTypes";
import {HOSTNAME} from "../../constants/appConstants";

import axios from 'axios';

export function checkout(payload) {
    console.log("getStoresByAdmin payload");
    console.log(payload);

    return (dispatch) => {
        axios.post(`http://${HOSTNAME}:8080/order/checkout/`, null, {params: payload} )
            .then((response) => dispatch(checkoutDispatch(response.data)))
            .catch((err) => console.log(err));
    }
}

export const checkoutDispatch = (returnData) => {
    console.log("getStoresByAdminDispatch returnData");
    console.log(returnData);

    return {type: CHECKOUT, payload: returnData}
};

export function markDeliveryNotReceived(payload) {
    console.log("markDeliveryNotReceived payload");
    console.log(payload);

    return (dispatch) => {
        axios.post(`http://${HOSTNAME}:8080/order/markDeliveryNotReceived/`, null, {params: payload} )
            .then((response) => dispatch(markDeliveryNotReceivedDispatch(response.data)))
            .catch((err) => console.log(err));
    }
}

export const markDeliveryNotReceivedDispatch = (returnData) => {
    console.log("markDeliveryNotReceivedDispatch returnData");
    console.log(returnData);

    return {type: DELIVERY_NOT_RECEIVED, payload: returnData}
};

export function getOrdersPlaced(payload) {
    console.log("getOrdersPlaced payload");
    console.log(payload);

    return (dispatch) => {
        axios.post(`http://${HOSTNAME}:8080/order/markDeliveryNotReceived/`, null, {params: payload} )
            .then((response) => dispatch(getOrdersPlacedDispatch(response.data)))
            .catch((err) => console.log(err));
    }
}

export const getOrdersPlacedDispatch = (returnData) => {
    console.log("getOrdersPlacedDispatch returnData");
    console.log(returnData);

    return {type: GET_PLACED_ORDERS, payload: returnData}
};


