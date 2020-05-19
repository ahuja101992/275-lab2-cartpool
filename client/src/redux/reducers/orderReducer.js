import {
    DELIVERY_NOT_RECEIVED,
    GET_ORDERS_BY_USER_ID,
    GET_ORDERS_READY_FOR_DELIVERY,
    GET_ORDERS_READY_FOR_PICKUP,
    MARK_DELIVERED,
    PICKUP_ORDER
} from "../../redux/constants/actionTypes";
import {PICKED_UP, PLACED} from "../../constants/appConstants";

const initialState = {
    ordersReadyForPickup: [],
    ordersReadyForDelivery: [],
    orderByPooler: [],
    //ordersReadyForPickup: [{"orderId": "1", "customerName": "xyz", "customerAddress": "1SM", "status": "OK"}],
    // orderByPooler: [{"orderId": "1", "customerName": "xyz", "customerAddress": "1SM", "status": "Placed"},
    //     {"orderId": "2", "customerName": "abc", "customerAddress": "2SM", "status": "Picked-up by oneself"},
    //     {"orderId": "3", "customerName": "pqr", "customerAddress": "3SM", "status": "Delivered"}]
};

const extractAddress = (address) => {
    return address.street + " " + address.city + " " + address.state + " " + address.zip
}

const getOrdersBasedOnStatus = (response, applyFilter, status) => {
    console.log("getOrderBasedOnStatus")
    console.log(response)

    let ordersByStatus = response;

    if (applyFilter) {
        ordersByStatus = response.filter(order => {
            return (order.status === status)
        });
    }


    const displayOrders = [];

    ordersByStatus.forEach(function (order) {
        const displayOrder = {};
        //const items = JSON.parse(order.items);

        displayOrder["status"] = order.status;
        displayOrder["orderId"] = order.id;
        displayOrder["customerName"] = order.orderOwner.screenname;
        displayOrder["customerAddress"] = extractAddress(order.orderOwner.address);
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
    console.log("orderReducer:");
    console.log(action.payload);


    if (action.type === PICKUP_ORDER) {
        return Object.assign({}, state, {
            ordersReadyForPickup: getOrdersBasedOnStatus(action.payload, true, PLACED)
        });
    } else if (action.type === MARK_DELIVERED) {
        return Object.assign({}, state, {
            ordersReadyForDelivery: getOrdersBasedOnStatus(action.payload, true, PICKED_UP)
        });
    } else if (action.type === DELIVERY_NOT_RECEIVED) {
        return Object.assign({}, state, {
            orderByPooler: action.payload
        });
    } else if (action.type === GET_ORDERS_BY_USER_ID) {
        return Object.assign({}, state, {
            orderByPooler: action.payload
        });
    } else if (action.type === GET_ORDERS_READY_FOR_PICKUP) {
        return Object.assign({}, state, {
            ordersReadyForPickup: getOrdersBasedOnStatus(action.payload, true, PLACED)
        });
    } else if (action.type === GET_ORDERS_READY_FOR_DELIVERY) {
        return Object.assign({}, state, {
            ordersReadyForDelivery: getOrdersBasedOnStatus(action.payload, true, PICKED_UP)
        });
    }


    return state;
}