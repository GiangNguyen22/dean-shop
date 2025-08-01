import {createSlice} from '@reduxjs/toolkit';

const initialState = {
    loading: false
}

const commonSlice = createSlice({
    name: "commonSlice",
    initialState: initialState,
    reducers: {
        setLoading: (state, action) => {
            state.loading = action.payload
        }
    }
});

export const {setLoading} = commonSlice?.actions;
export default commonSlice.reducer;