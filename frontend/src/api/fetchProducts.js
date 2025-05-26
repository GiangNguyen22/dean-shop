import axios from "axios";
import { API_BASE_URL, API_URLS } from "./constant";

export const getAllProducts = async(id, typeId) =>{
    let url = API_BASE_URL + API_URLS.GET_PRODUCTS + `?categoryId=${id}`;
    if(typeId){
        url = url + `&typeId=${typeId}` ;
    }
    try{
        const result = await axios(url, {
            method: 'GET'
        })
        return result?.data;
    }catch(e){
        console.log(e);
    }
}

export const getProductBySlug = async(slug) => {
    const url = API_BASE_URL + API_URLS.GET_PRODUCTS + `?slug=${slug}`;
    try{
        const reusult = await axios(url, {
            method: 'GET'
        })
        return reusult?.data?.[0];
    }catch(err){
        console.log(err);
    }
}