import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import axios from 'axios';

export const fetchProducts = createAsyncThunk('product/all-products', async () => {
    const response = await axios.get('http://localhost:8080/product/all-products');
    return response.data;
});

export const createOrder = createAsyncThunk('order/createOrder', async order => {
    const response = await axios.post('http://localhost:8080/order/create-order', order);
    return response.data;
});

const productSlice = createSlice({
    name: 'products',
    initialState: {
        products: [],
        status: 'idle',
        error: null,
        order: null
    },
    reducers: {
        setOrder: (state, action) => {
            state.order = action.payload;
        }
    },
    extraReducers: (builder) => {
        builder
            .addCase(fetchProducts.pending, (state) => {
                state.status = 'loading';
            })
            .addCase(fetchProducts.fulfilled, (state, action) => {
                if (action.payload?.status !== 200) {
                    state.status = 'failed';
                    state.error = action.payload?.message;
                } else {
                    state.status = 'succeeded';
                    state.products = action.payload?.data || [];
                }
            })
            .addCase(fetchProducts.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            })
            .addCase(createOrder.pending, (state) => {
                state.status = 'loading';
            })
            .addCase(createOrder.fulfilled, (state, action) => {
                if (action.payload?.status !== 200) {
                    state.status = 'failed';
                    state.error = action.payload?.message;
                } else {
                    state.status = 'succeeded';
                    state.order = action.payload?.data || [];
                }
            })
            .addCase(createOrder.rejected, (state, action) => {
                state.status = 'failed';
                state.error = action.error.message;
            })
    },
});

export const { setOrder } = productSlice.actions;

export default productSlice.reducer;