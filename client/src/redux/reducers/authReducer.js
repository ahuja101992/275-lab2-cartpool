import {SIGN_IN, SIGN_IN_ERROR, SIGN_UP, VERIFY_EMAIL, VERIFY_EMAIL_ERROR} from "../../redux/constants/actionTypes";

const initialState = {
    signupSuccess: null,
    signupMessage: null,
    signinSuccess: null,
    signinMessage: null,
    verifyEmailSuccess: null,
    verifyEmailMessage: null,
    userType: null,
    token: null,
    userId: null,
    userActive: null
};

const isAdmin = (email = localStorage.getItem("email")) => {
    return "sjsu.edu" === email.split("@")[1];
};

export default function authReducer(state = initialState, action) {
    console.log("signin auth reducer:");
    console.log(action.payload);

    if (action.type === SIGN_IN) {
        localStorage.setItem('id', action.payload.id);
        localStorage.setItem('screenname', action.payload.screenname);
        localStorage.setItem('email', action.payload.email);
        localStorage.setItem('type', isAdmin(action.payload.email) ? "admin" : "pooler");

        return Object.assign({}, state, {
            signinSuccess: action.payload.id !== null ? true : false,
        });
    } else if (action.type === SIGN_IN_ERROR) {
        return Object.assign({}, state, {
            signinSuccess: false,
            signinMessage: "Login failed, incorrect username or password",
        });
    } else if (action.type === SIGN_UP) {
        return Object.assign({}, state, {
            signupSuccess: action.payload.id !== null ? true : false,
            signupMessage: "",
        });
    } else if (action.type === VERIFY_EMAIL) {
        return Object.assign({}, state, {
            verifyEmailSuccess: action.payload.is_verified !== null ? true : false,
            verifyEmailMessage: action.payload.is_verified ? "You account has been successfully verified. Please login below." : "Could not verify account"
        });
    } else if (action.type === VERIFY_EMAIL_ERROR) {
        return Object.assign({}, state, {
            verifyEmailSuccess: true,
            verifyEmailMessage: "Could not verify account"
        });
    }

    return state;
}