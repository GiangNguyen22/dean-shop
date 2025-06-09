import {createSlice} from '@reduxjs/toolkit';


const initialState = {
    userInfo:{},
    orders: []
}
const userSlice= createSlice({
    name: "userSlice",
    initialState: initialState,
    reducers: {
        loadUserInfo: (state, action)=> {
            return {
                ...state,
                userInfo: action?.payload
            }
        },
        saveAddress: (state, action) => {
            const addresses = [...(state?.userInfo?.addressList || [])];
            addresses.push(action?.payload);
            return {
                ...state,
                userInfo:{
                    ...state?.userInfo,
                    addressList: addresses
                }

            }
        },
        removeAddress: (state, action)=> {
            return {
                ...state,
                userInfo:{
                    ...state?.userInfo,
                    addressList: state?.userInfo?.addressList.filter(address => address?.id !== action?.payload)
                }
            }
        },
        updateAddress: (state, action)=> {
            const updatedAddress = action?.payload;
            const updateList = state?.userInfo?.addressList.map((item) => {
                if(item?.id === updatedAddress?.id){
                    return updatedAddress;
                }
                return item;
            });

            return {
                ...state,
                userInfo:{
                    ...state.userInfo,
                    addressList: updateList
                }
            }
        },
         loadOrders: (state,action)=>{
            return {
                ...state,
                orders:action?.payload
            }
        },
        cancelOrder: (state,action)=>{
            return {
                ...state,
                orders:state?.orders?.map(order=>{
                    if(order?.id === action?.payload){
                        return {
                            ...order,
                            orderStatus:'CANCELLED'
                        }
                    }
                    return order;
                })
            }
        }
        
    }
})


export const {loadUserInfo, saveAddress, removeAddress, updateAddress, loadOrders, cancelOrder} = userSlice?.actions;
export const selectUserInfo = (state) => state?.userState?.userInfo ?? {};
export const selectIsUserAdmin = (state) => state?.userState?.userInfo?.authorityList?.find((authority) => authority?.roleCode === 'ADMIN')?.authority === 'ADMIN';
export const selectAllOrders = (state) => state?.userState?.orders ?? [];
export default userSlice.reducer;