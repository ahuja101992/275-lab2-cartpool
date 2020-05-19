import {CREATE_STORE, CREATE_STORE_ERROR, DELETE_STORE, GET_STORES_BY_ADMIN} from "../../redux/constants/actionTypes";
import {HOSTNAME} from "../../constants/appConstants";

import axios from 'axios';

export function getStoresByAdmin(payload) {
    console.log("getStoresByAdmin payload");
    console.log(payload);

    return (dispatch) => {
        axios.get(`http://${HOSTNAME}:8080/inventory/store/getByAdmin/${payload.adminId}`)
            .then((response) => dispatch(getStoresByAdminDispatch(response.data)))
            .catch((err) => console.log(err));
    }
}

export const getStoresByAdminDispatch = (returnData) => {
    console.log("getStoresByAdminDispatch returnData");
    console.log(returnData);

    return {type: GET_STORES_BY_ADMIN, payload: returnData}
};

export function createStore(payload) {
    console.log("createStore payload");
    console.log(payload);

    return (dispatch) => {
        axios.post(`http://${HOSTNAME}:8080/inventory/store`, null, {params: payload})
            .then((response) => dispatch(createStoreDispatch(response.data)))
            .catch((err) => dispatch(createStoreErrorDispatch(err)));
    }
}

export const createStoreDispatch = (returnData) => {
    console.log("createStoreDispatch returnData");
    console.log(returnData);

    return {type: CREATE_STORE, payload: returnData}
};

export const createStoreErrorDispatch = (returnData) => {
    console.log("createStoreErrorDispatch returnData");
    console.log(returnData);

    return {type: CREATE_STORE_ERROR, payload: returnData}
};

export function deleteStore(payload) {
    console.log("deleteStore payload");
    console.log(payload);

    return (dispatch) => {
        axios.delete(`http://${HOSTNAME}:8080/inventory/store/${payload.storeId}/${payload.adminId}`, null,)
            .then((response) => dispatch(deleteStoreDispatch(response.data)))
            .catch((err) => console.log(err));
    }
}

export const deleteStoreDispatch = (returnData) => {
    console.log("deleteStoreDispatch returnData");
    console.log(returnData);

    return {type: DELETE_STORE, payload: returnData}
};


