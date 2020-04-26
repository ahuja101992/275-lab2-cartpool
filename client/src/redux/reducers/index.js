import {combineReducers} from "redux";
import authReducer from "./authReducer";
import inventoryReducer from "./inventoryReducer";
import orderReducer from "./orderReducer";

const rootReducer = combineReducers({
    auth: authReducer,
    inventory: inventoryReducer,
    orders: orderReducer
});
export default rootReducer;
