import React, { useMemo } from 'react'
import SvgStarIcon from '../common/SvgStarIcon'
import SvgEmptyStar from '../common/SvgEmptyStar'

const Rating = ({ratings}) => {
    const ratingNumber = useMemo(() => {
        const validRating = Math.floor(Number(ratings));
        return Number.isNaN(validRating) || validRating < 0
          ? []
          : Array(validRating).fill();
      }, [ratings]);
      
  return (
    <div className='flex items-center'>
        {
            ratingNumber?.map((_, index)=>(
                <SvgStarIcon key={index}/>
            ))
        }
        {
            new Array(5-ratingNumber?.length).fill().map((_, index)=>(
                <SvgEmptyStar key={'empty-' + index} />
            ))
        }
        <p className='px-2 text-gray-500'>{ratings}</p>
    </div>
  )
}

export default Rating