import React, { useMemo } from 'react'
import FilterIcon from '../../components/common/FilterIcon'
import content from '../../components/data/content.json'
import Categories from '../../components/Filter/Categories';
import PriceFilter from '../../components/Filter/PriceFilter';
import ColorsFilter from '../../components/Filter/ColorsFilter';
import SizeFilter from '../../components/Filter/SizeFilter';
import ProductCard from './ProductCard';
const categories = content?.categories;

const ProductListPage = ({categoryType}) => {
    
    const categoryContent = useMemo(()=> {
       return categories?.find((category)=> category.code === categoryType)
    }, [categoryType])//Chỉ lọc lại khi categorytype thay đổi

    const productListItem = useMemo(()=>{
        return content?.products?.filter(product => product.category_id === categoryContent.id)
    }, [categoryContent])
    
    
  return (
    <div>
        <div className='flex'>
            <div className='w-[20%] p-[10px] m-[20px] border rounded-md'>
                {/* Filter */}
                <div className='flex justify-between'>
                    <p className='text-gray-600'>Filter</p>
                    <FilterIcon/>
                </div>
                <div>
                    {/* Product type */}
                    <p className='text-[16px] text-black mt-5'>Categories</p>
                    <Categories  types={categoryContent?.types}/>
                    <hr></hr>
                </div>
                {/* Price */}
                <PriceFilter/>
                <hr></hr>
                {/* Color */}
                <ColorsFilter  colors={categoryContent?.meta_data?.colors}/>
                <hr></hr>
                {/* Size */}
                <SizeFilter sizes={categoryContent?.meta_data?.sizes || []}/>
            </div>
            <div className='p-[15px]'>
                <p className='text-black text-lg'>{categoryContent?.description}</p>
                {/* Products */}
                <div className='pt-4 grid grid-cols-1 lg:grid-cols-4 md:grid-cols-2 gap-8 px-2'>
                    {productListItem?.map((item, index)=>
                        <ProductCard key={index} {...item}/>
                    )}
                </div>
                
            </div>
        </div>
    </div>
  )
}

export default ProductListPage