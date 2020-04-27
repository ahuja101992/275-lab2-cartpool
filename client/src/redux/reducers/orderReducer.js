import {CHECKOUT, DELIVERY_NOT_RECEIVED, GET_ORDERS_BY_USER_ID, GET_ORDERS_READY_FOR_PICKUP} from "../../redux/constants/actionTypes";

const initialState = {
    ordersReadyForPickup: [],
    ordersReadyForDelivery: [],
    orderByPooler: [],
    //ordersReadyForPickup: [{"orderId": "1", "customerName": "xyz", "customerAddress": "1SM", "status": "OK"}],
    // orderByPooler: [{"orderId": "1", "customerName": "xyz", "customerAddress": "1SM", "status": "Placed"},
    //     {"orderId": "2", "customerName": "abc", "customerAddress": "2SM", "status": "Picked-up by oneself"},
    //     {"orderId": "3", "customerName": "pqr", "customerAddress": "3SM", "status": "Delivered"}]
};

const getOrdersBasedOnStatus = (response, status) => {
    console.log("getOrderBasedOnStatus")
    console.log(response)

    // const ordersByStatus = response.filter(order => {
    //     return (order.status === status)
    // });

    const displayOrders = [];

    response.forEach(function (order) {
        const displayOrder = {};
        //const items = JSON.parse(order.items);

        displayOrder["status"] = order.status;
        displayOrder["orderId"] = order.id;
        displayOrder["customerName"] = order.orderOwner.firstname + " " + order.orderOwner.lastname;
        displayOrder["customerAddress"] = order.orderOwner.address;
        displayOrder["finalPrice"] = "$" + order.finalPrice;
        // displayOrder["items"] = [];
        //
        // items.items.forEach(function (item) {
        //     const line = `Name: ${item.name} Quantity: ${item.quantity} Price: ${item.price}`;
        //     displayOrder.items.push(line);
        // });

        displayOrders.push(displayOrder);
    });

    return displayOrders;
}

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
    } else if (action.type === GET_ORDERS_BY_USER_ID) {
        return Object.assign({}, state, {
            orderByPooler: action.payload
        });
    } else if (action.type === GET_ORDERS_READY_FOR_PICKUP) {

        return Object.assign({}, state, {
            ordersReadyForPickup: getOrdersBasedOnStatus(action.payload)
        });
    }

    return state;
}