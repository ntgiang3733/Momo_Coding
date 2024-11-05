// store.js
import { configureStore } from '@reduxjs/toolkit';
import productReducer from './page/product/productSlice';

const store = configureStore({
    reducer: {
        products: productReducer,
    },
});

export default store;