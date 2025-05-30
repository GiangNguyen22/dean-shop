import {jwtDecode} from 'jwt-decode';

export const isTokenValid = () =>{
    const token = localStorage.getItem('authToken');
    if(!token) return false;

    try{
        const decoded = jwtDecode(token);
        const currentTime = Date.now() / 1000 ; //curent time in seconds

        return decoded.exp > currentTime;
    }
    catch(err){
        console.log("Invalid token", err);
        return false;
    }
}

export const saveToken = (token) =>{
    localStorage.setItem('authToken', token);
}

export const logOut = () =>{
    localStorage.removeItem('authToken');
}

export const getToken = ()=>{
    return localStorage.getItem('authToken');
}