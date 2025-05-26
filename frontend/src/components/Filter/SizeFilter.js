import React, { useState } from 'react'

const SizeFilter = ({sizes, hiddenTitle}) => {
    const [appliedSize, setAppliedSize] = useState([])
    const onClickDiv = (item)=>{
        setAppliedSize((prevSize) => prevSize.includes(item)? prevSize.filter(size => size!==item) : [...prevSize, item])
        console.log(appliedSize)
    }
  return (
    <div className='mb-4'>
        {!hiddenTitle && <p className='text-[16px] text-black mt-5 mb-5'>Size</p>}
        <div className='flex flex-wrap px-2'>
            {sizes?.map((item, index)=>{
                return (
                    <div className='flex mr-2' key={index}> 
                        <div className='border rounded-lg text-center hover:scale-105 mr-4 mb-4 w-[50px] cursor-pointer
                            border-gray-500 text-gray-500' style={appliedSize?.includes(item)? {background:'black', color:'white'} : {}} onClick={()=> onClickDiv(item)}> 
                            {item}
                        </div>
                    </div>
                )
            })}
        </div>
    </div>
  )
}

export default SizeFilter