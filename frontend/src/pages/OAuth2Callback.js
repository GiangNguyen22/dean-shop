import React, { useEffect } from 'react'
import { saveToken } from '../utils/jwt-helper';
import { useNavigate } from 'react-router-dom';

const OAuth2Callback = () => {
    const navigate = useNavigate();
    //lay query tu URL
    useEffect(() => {
        const params = new URLSearchParams(window.location.search);
        const token = params.get('token');

        if (token) {
            saveToken(token);
            navigate("/");
        } else {
            navigate("/v1/login")
        }
    }, [navigate]);


    return (
        <div></div>
    )
}

export default OAuth2Callback