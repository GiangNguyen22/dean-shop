import React from 'react'

const Categories = ({types}) => {
  return (
    <div>
      {types.map((type, index)=>{
        return (
        <div className='flex items-center p-1' key={index}>
          <input type='checkbox' name={type?.code} className='border rounded-xl w-4 h-4 accent-black'></input>
          <label htmlFor={type?.code}  className='px-2 text-gray-600 text-[14px]'> {type?.name}</label>
         </div>
        )
      })}
    </div>
  )
}

export default Categories