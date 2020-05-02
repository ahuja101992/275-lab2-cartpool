import {combineReducers} from "redux";
import authReducer from "./authReducer";
import inventoryReducer from "./inventoryReducer";
import orderReducer from "./orderReducer";
import messagingReducer from "./messagingReducer";

const rootReducer = combineReducers({
    auth: authReducer,
    inventory: inventoryReducer,
    orders: orderReducer,
    messaging: messagingReducer
});
export default rootReducer;
