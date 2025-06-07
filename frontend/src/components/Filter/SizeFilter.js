import React, { useCallback, useEffect, useState } from 'react'

const SizeFilter = ({sizes, hiddenTitle, multi=true, onChange}) => {
    const [appliedSize, setAppliedSize] = useState([])
    const onClickDiv =(item)=>{
        if(appliedSize.indexOf(item) > -1){
            setAppliedSize(pre => pre.filter(size => item !== size));
        }else{
            if(multi){
                setAppliedSize([...appliedSize, item]);
            }else{
                setAppliedSize([item])
            }
        }
        
        console.log(appliedSize)
    }

    useEffect(() => {
        onChange && onChange(appliedSize);
    }, [appliedSize, onChange]);

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