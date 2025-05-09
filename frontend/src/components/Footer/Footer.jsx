import React from 'react'
import FbIcon from '../common/FbIcon'
import InstaIcon from '../common/InstaIcon'

const Footer = ({content}) => {
  return (
    <div className='bg-black text-white py-8'>
      <div className='flex justify-evenly'>
          {content && content?.items.map((item, index)=> {
            return (
              <div className='flex flex-col' key={index}> 
                  <p className='text-[16px]'>{item.title}</p>
                  {item?.list && item?.list.map((itemList, index) => <a href={itemList.path} className='py-2 text-[12px]'
                  key={index + itemList.label}>{itemList.label}</a>)}
                  {item?.description && <p>{item.description}</p>}
              </div>
            )
          })}
      </div>
      <div className='flex gap-2 items-center justify-center py-4'>
          <a href='/fb'><FbIcon /></a>
          <a href='/insta'><InstaIcon /></a>  
        </div>
      <p className='text-[12px] text-center'>{content?.copyright}</p>
    </div>
    
  )
}

export default Footer