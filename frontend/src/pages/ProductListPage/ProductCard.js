import React from 'react'
import { Link } from 'react-router-dom'
import SvgFavourite from '../../components/common/SvgFavourite'

const ProductCard = ({ id, title, description, price, discoutnt, rating, brand, thumbnail, slug }) => {
    return (
        <div className="flex flex-col hover:scale-100 relative">
            <Link to={`/product/${slug}`} >
                <img className="h-[320px] w-[280px] border rounded bg-cover bg-center
                    hover:scale-105 cursor-pointer object-cover " src={thumbnail} alt={title} />
            </Link>

            <div className="flex justify-between items-center">
                <div className="flex flex-col pt-2">
                    <p className="text-[16px] p-1">{title}</p>
                    {description && <p className="text-gray-500 text-[12px] px-1">{brand}</p>}
                </div>
                <div>
                    <p>${price}</p>
                </div>

            </div>

            <button onClick={()=> console.log("Click button")} className='absolute top-0 right-0 pt-4 pr-4 cursor-pointer'><SvgFavourite /></button>
        </div>
    )
}

export default ProductCard