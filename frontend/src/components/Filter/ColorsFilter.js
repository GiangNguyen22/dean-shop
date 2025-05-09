import React, { useCallback } from 'react'
import { useState } from 'react';

export const colorSelector = {
    "Purple":"#8434E1",
    "Black":"#252525",
    "White":"#FFFFFF",
    "Gray": "#808080",
    "Blue": "#0000FF",
    "Red": "#FF0000",
    "Orange": "#FFA500",
    "Navy": "#000080",
    "Grey": "#808080",
    "Yellow": "#FFFF00",
    "Pink": "#FFC0CB",
    "Green": "#008000"

}

const ColorsFilter = ({colors}) => {
  const [appliedColors, setAppliedColors] = useState([]);
  const onClickDiv = useCallback((item)=>{
      setAppliedColors(prevColors => prevColors.includes(item)? prevColors.filter(color => color !== item) : [...prevColors, item])
  }, [])

  return (
    <div className='mb-4'>
      <p className='text-[16px] text-black mt-5'>Colors</p>
      <div className='flex flex-wrap ml-2 mt-4'> 
        {colors?.map((item, index)=> {  
          return (
            <div className='flex flex-col mr-2' key={index}>
              <div className=' w-8 h-8 border rounded-xl cursor-pointer hover:scale-110 mr-4' onClick={()=>onClickDiv(item)} style={{background:`${colorSelector[item]}`}}></div>
              <p className='text-sm text-gray-400 mb-2' style={{color:`${appliedColors?.includes(item) ? 'black':''}`}}>{item}</p>
             </div>
          )
        })}

      </div>
    </div>
  )
}

export default ColorsFilter