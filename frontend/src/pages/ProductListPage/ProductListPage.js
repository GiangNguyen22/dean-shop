import React, { useEffect, useMemo, useState } from 'react'
import FilterIcon from '../../components/common/FilterIcon'
import content from '../../components/data/content.json'
import Categories from '../../components/Filter/Categories';
import PriceFilter from '../../components/Filter/PriceFilter';
import ColorsFilter from '../../components/Filter/ColorsFilter';
import SizeFilter from '../../components/Filter/SizeFilter';
import ProductCard from './ProductCard';
import { useDispatch, useSelector } from 'react-redux';
import { setLoading } from '../../store/features/common';
import { getAllProducts } from '../../api/fetchProducts';
const categories = content?.categories;


//categoryType như là MEN/WOMMEN        
const ProductListPage = ({categoryType}) => {
    console.log(categories)
    const dispatch = useDispatch();

    // lấy các category
    const categoryData = useSelector((state) => state?.categoryState?.categories);
    const [products, setProducts] = useState([]);
    
    const categoryContent = useMemo(()=> {
       return categories?.find((category)=> category.code === categoryType)
    }, [categoryType])//Chỉ lọc lại khi categorytype thay đổi
    console.log(categoryContent)

    const productListItem = useMemo(()=>{
        return content?.products?.filter(product => product.category_id === categoryContent.id)
    }, [categoryContent])


    // lấy category theo code vd: Men/Women
    const category = useMemo(()=>{
        return categoryData?.find(element => element?.code === categoryType);
    }, [categoryData, categoryType]);

    console.log(category);

    useEffect(()=>{

        dispatch(setLoading(true));
        if(category?.id){
            getAllProducts(category?.id).then(res => {
            setProducts(res);
        }).catch(err => {

        }).finally(()=>{
            dispatch(setLoading(false))
        })
        }
        
    }, [category?.id,  dispatch] )
    
    
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
                <p className='text-black text-lg'>{category?.description}</p>
                {/* Products */}
                <div className='pt-4 grid grid-cols-1 lg:grid-cols-4 md:grid-cols-2 gap-8 px-2'>
                    {products?.map((item, index)=>
                        <ProductCard key={item?.id+"_"+index} {...item} title={item?.name}/>
                    )}
                </div>
                
            </div>
        </div>
    </div>
  )
}

export default ProductListPage