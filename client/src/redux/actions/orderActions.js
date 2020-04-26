import {CHECKOUT, DELIVERY_NOT_RECEIVED, GET_ORDERS_BY_USER_ID} from "../../redux/constants/actionTypes";
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

export function getOrdersByUserId(payload) {
    console.log("getOrdersPlaced payload");
    console.log(payload);

    return (dispatch) => {
        axios.get(`http://${HOSTNAME}:8080/order/getOrdersByUserId/${payload.userId}` )
            .then((response) => dispatch(getOrdersByUserIdDispatch(response.data)))
            .catch((err) => console.log(err));
    }
}

export const getOrdersByUserIdDispatch = (returnData) => {
    console.log("getOrdersPlacedDispatch returnData");
    console.log(returnData);

    return {type: GET_ORDERS_BY_USER_ID, payload: returnData}
};


