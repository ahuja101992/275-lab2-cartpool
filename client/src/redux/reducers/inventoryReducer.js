import {CREATE_STORE, CREATE_STORE_ERROR, DELETE_STORE, GET_STORES_BY_ADMIN} from "../../redux/constants/actionTypes";


const initialState = {
    stores: [],
    createStoreSuccess: null,
    createStoreMessage: ""
};

export default function inventoryReducer(state = initialState, action) {
    console.log("inventoryReducer:");
    console.log(action.payload);

    if (action.type === CREATE_STORE) {
        return Object.assign({}, state, {
            createStoreSuccess: true,
            stores: action.payload
        });
    } else if (action.type === CREATE_STORE_ERROR) {
        return Object.assign({}, state, {
            createStoreSuccess: false,
            createStoreMessage: "Error - please make sure name is unique"
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