import React from 'react'
import SeactionHeading from '../SectionHeading/SeactionHeading'
import Card from '../../Card/Card'

const Category = ({title, data}) => {
  return (
    <>
        <SeactionHeading title={title}/>
        <div className='flex items-center flex-wrap px-8'>
          {data && data?.map((item, index)=>{
            return (
              <Card key={index} title={item.title} desccription={item.description} imagePath={item.image}
                actionArrow={true}/>
            )
          })}
        </div>
    </>
  )
}

export default Category