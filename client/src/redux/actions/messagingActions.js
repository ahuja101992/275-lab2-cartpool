import {GET_ALL_POOLERS} from "../../redux/constants/actionTypes";
import {HOSTNAME} from "../../constants/appConstants";

import axios from 'axios';

export function getAllPollers(payload) {
    console.log("getAllPollers payload");
    console.log(payload);

    return (dispatch) => {
        axios.get(`http://${HOSTNAME}:8080/pooler/messaging/getAll`)
            .then((response) => dispatch(getAllPollersDispatch(response.data)))
            .catch((err) => console.log(err));
    }
}

export const getAllPollersDispatch = (returnData) => {
    console.log("getStoresByAdminDispatch returnData");
    console.log(returnData);

    return {type: GET_ALL_POOLERS, payload: returnData}
};

export function sendMessage(payload) {
    console.log("sendMessage payload");
    console.log(payload);

    return (dispatch) => {
        axios.post(`http://${HOSTNAME}:8080/pooler/messaging/sendMessage`, null, {params: payload})
            .then((response) => console.log(response))
            .catch((err) => console.log(err));
    }
}
