import {GET_STORES_BY_ADMIN, SIGN_IN_ERROR, SIGN_UP} from "../../redux/constants/actionTypes";
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