import {GET_STORES_BY_ADMIN, CREATE_STORE} from "../../redux/constants/actionTypes";
import {HOSTNAME} from "../../constants/appConstants";

import axios from 'axios';

export function getStoresByAdmin(payload) {
    console.log("signIn payload");
    console.log(payload);

    return (dispatch) => {
        axios.get(`http://${HOSTNAME}:8080/inventory/store/getByAdmin/${payload.adminId}`)
            .then((response) => dispatch(getStoresByAdminDispatch(response.data)))
            .catch((err) => console.log(err));
    }
}

export const getStoresByAdminDispatch = (returnData) => {
    console.log("Inside signInDispatch");
    console.log(returnData);

    return {type: GET_STORES_BY_ADMIN, payload: returnData}
};

export function createStore(payload) {
    console.log("signIn payload");
    console.log(payload);

    return (dispatch) => {
        axios.post(`http://${HOSTNAME}:8080/inventory/store`, null, {params: payload})
            .then((response) => dispatch(createStoreDispatch(response.data)))
            .catch((err) => console.log(err));
    }
}

export const createStoreDispatch = (returnData) => {
    console.log("Inside signInDispatch");
    console.log(returnData);

    return {type: CREATE_STORE, payload: returnData}
};

