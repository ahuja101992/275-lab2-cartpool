import {combineReducers} from "redux";
import authReducer from "./authReducer";
import inventoryReducer from "./inventoryReducer";

const rootReducer = combineReducers({
    auth: authReducer,
    inventory: inventoryReducer
});
export default rootReducer;
