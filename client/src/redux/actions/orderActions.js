import {CHECKOUT} from "../../redux/constants/actionTypes";
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