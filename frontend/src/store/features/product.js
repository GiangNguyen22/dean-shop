import {createSlice} from '@reduxjs/toolkit';


const initialState = {
    products:[]
}
const productSlice = createSlice({
    name: "productSlice",
    initialState: initialState,
    reducers: {
        addProduct : (state, action)=>{
            state.products.push(action.payload)
        },

        loadProducts: (state, action) => {
            state.products = action.payload
        }

    }
})

export const {addProduct, loadProducts} = productSlice.actions;
export default productSlice.reducer;