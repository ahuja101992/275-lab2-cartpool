import {GET_ALL_POOLERS, DELETE_STORE, GET_STORES_BY_ADMIN} from "../../redux/constants/actionTypes";


const initialState = {
    allPoolers: []
};

export default function messagingReducer(state = initialState, action) {
    console.log("messagingReducer:");
    console.log(action.payload);

    if (action.type === GET_ALL_POOLERS) {
        return Object.assign({}, state, {
            allPoolers: action.payload
        });
    } else if (action.type === GET_STORES_BY_ADMIN) {
        return Object.assign({}, state, {
            stores: action.payload
        });
    } else if (action.type === DELETE_STORE) {
        return Object.assign({}, state, {
            stores: action.payload
        });
    }
    return state;
}