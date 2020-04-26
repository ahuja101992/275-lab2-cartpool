import {CHECKOUT, DELIVERY_NOT_RECEIVED} from "../../redux/constants/actionTypes";

const initialState = {
    ordersReadyForCheckout: [{"orderId": "1", "customerName": "xyz", "customerAddress": "1SM", "status": "OK"}],
    orderByPooler: [{"orderId": "1", "customerName": "xyz", "customerAddress": "1SM", "status": "Placed"},
        {"orderId": "2", "customerName": "abc", "customerAddress": "2SM", "status": "Picked-up by oneself"},
        {"orderId": "3", "customerName": "pqr", "customerAddress": "3SM", "status": "Delivered"}]
};

export default function orderReducer(state = initialState, action) {
    console.log("inventoryReducer:");
    console.log(action.payload);

    if (action.type === CHECKOUT) {
        return Object.assign({}, state, {
            ordersReadyForCheckout: [...state.ordersReadyForCheckout]
        });
    } else if (action.type === DELIVERY_NOT_RECEIVED) {
        return Object.assign({}, state, {
            orderByPooler: [...state.orderByPooler]
        });
    }

    return state;
}