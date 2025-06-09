import { combineReducers, configureStore } from "@reduxjs/toolkit";
import productReducer from './features/product';
import cartProducer from './features/cart';
import categoryProducer from './features/category';
import commonProducer from './features/common';
import userReducer from './features/user';

const rootReducer = combineReducers({
    productState: productReducer,
    cartState: cartProducer,
    categoryState: categoryProducer,
    commonState: commonProducer,
    userState: userReducer
})

const store = configureStore({
    reducer: rootReducer
})

export default store;