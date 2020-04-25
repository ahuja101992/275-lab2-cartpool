import {CHECKOUT} from "../../redux/constants/actionTypes";

const initialState = {
    ordersReadyForCheckout: [{"orderId": "1", "customerName": "xyz", "customerAddress": "1SM", "status": "OK"}]
};

export default function orderReducer(state = initialState, action) {
    console.log("inventoryReducer:");
    console.log(action.payload);

    if (action.type === CHECKOUT) {
        return Object.assign({}, state, {
            ordersReadyForCheckout: [...state.ordersReadyForCheckout]
        });
    }

    return state;
}