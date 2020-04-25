import {CHECKOUT} from "../../redux/constants/actionTypes";

const initialState = {
    stores: []
};

export default function orderReducer(state = initialState, action) {
    console.log("inventoryReducer:");
    console.log(action.payload);

    if (action.type === CHECKOUT) {
        return Object.assign({}, state, {
            stores: action.payload
        });
    }

    return state;
}