import React, { useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { isTokenValid } from '../../utils/jwt-helper';

const ProtectedRoute = ({children}) => {
  const navigate = useNavigate();
  useEffect(()=>{
    if(!isTokenValid()){
        navigate("/v1/login")
    }
  },[navigate]);
  return (
    <div>
        {/* chỉ khi đăng nhập mới truy cập được component con, không thì chuyển về trang đăng nhập */}
        {/* component con */}
        {children}
    </div>
  )
}

export default ProtectedRoute