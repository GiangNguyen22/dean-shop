import { API_BASE_URL } from "./constant"
import axios from "axios";
import { getHeaders } from "./constant";
export const fetchUserDetail = async() => {
    const url = API_BASE_URL + '/user/profile';
    try{
        const response = await axios (url, {
            method: "GET",
            headers: getHeaders();
        })
        return response?.data;
    }
    catch(err){
        throw new Error(err);
    }
}

