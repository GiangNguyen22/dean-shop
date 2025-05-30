import {API_BASE_URL} from './constant'
import axios from 'axios';

export const loginAPI = async(body)=> {
    const url = API_BASE_URL + '/api/auth/login';
    try{
        const resposne = await axios(url, {
            method: "POST",
            data: body
        });
        return resposne?.data;
    }
    catch(err){
        throw new Error(err);
    }
}

export const registerAPI = async (body) => {
    const url = API_BASE_URL + '/api/auth/register';
    try{
        const response = await axios (url, {
            method: "POST",
            data: body
        });
        return response?.data;
    }
    catch(err){
        throw new Error(err);
    }
}

export const verifyAPI = async (body) =>{
    const url = API_BASE_URL + '/api/auth/verify';
    try{
        const response = await axios (url, {
            method: "POST",
            data: body
        });
        return response?.data;
    }
    catch(err){
        throw new Error(err);
    }
}