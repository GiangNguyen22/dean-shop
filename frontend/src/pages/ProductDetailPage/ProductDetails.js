import React, { useState, useMemo, useEffect } from 'react'
import { Link, useLoaderData } from 'react-router-dom'
import Breadcrumb from '../../components/BreadCrumb/Breadcrumb';
import content from '../../components/data/content.json'
import Rating from '../../components/Rating/Rating';
import SizeFilter from '../../components/Filter/SizeFilter';
import ProductColor from './ProductColor';
import SvgCreditCard from '../../components/common/SvgCreditCard';
import SvgCloth from '../../components/common/SvgCloth';
import SvgShipping from '../../components/common/SvgShipping';
import SvgReturn from '../../components/common/SvgReturn';
import SeactionHeading from '../../components/Sections/SectionHeading/SeactionHeading';
import ProductCard from '../ProductListPage/ProductCard';

const categories = content?.categories;
const extraSections = [
    {
      icon:<SvgCreditCard />,
      label:'Secure payment'
    },
    {
      icon:<SvgCloth />,
      label:'Size & Fit'
    },
    {
      icon:<SvgShipping />,
      label:'Free shipping'
    },
    {
      icon:<SvgReturn />,
      label:'Free Shipping & Returns'
    }
  ]

const ProductDetails = () => {
    const product = useLoaderData();
    const [image, setImage] = useState(product?.thumbnail);
    const [breadCrumbLinks, setBreadCrumbLink] = useState([]);

    const similarProduct = useMemo(()=> {
        return content?.products?.filter((item)=> item?.type_id === product?.type_id && item?.id !== product?.id)
    }, [product])

    const productCategory = useMemo(() => {
        return categories?.find((category) => category?.id === product?.category_id);
      }, [product, categories]);

      useEffect(() => {
        setBreadCrumbLink([]);
        const arrayLinks = [{ title: 'Shop', path: '/' }, {
          title: productCategory?.name,
          path: productCategory?.path
        }];
        const productType = productCategory?.types?.find((item) => item?.type_id === product?.type_id);
       
        if (productType) {
          arrayLinks?.push({
            title: productType?.name,
            path: productType?.name
          })
        }
        setBreadCrumbLink(arrayLinks);
      }, [productCategory, product]);

    return (
        <>
        <div className='flex flex-col md:flex-row px-10'>
            <div className='w-[100%] md:w-[40%] lg:w-[50%]'>
                {/* Image */}
                <div className='flex flex-col md:flex-row'>
                    {/* Stack Image */}
                    <div className='w-[100%] md:w-[20%] h-[40px] md:h-[420px]'>
                        <div className='flex flex-row md:flex-col justify-center h-full'>
                            {
                               product?.images[0]?.startsWith('http') && product?.images?.map((item, index) => (
                                    <button key={index} onClick={() => setImage(item)} className='rounded-lg w-fit p-2 mb-2'><img src={item} className='h-[60px] w-[60px] rounded-lg bg-cover bg-center hover:scale-105 hover:border' alt={'sample-' + index} /></button>
                                ))
                            }
                        </div>

                    </div>
                    <div className='w-full md:w-[80%] flex justify-center md:pt-0 pt-10 mb-2'>
                        <img src={image} className='h-full w-full max-h-[520px] md:max-h-[560px] border rounded-lg cursor-pointer object-cover' alt={product?.title} />
                    </div>

                </div>
            </div>
            <div className='w-[60%] px-12 '>
                {/* Product Description */}
                <Breadcrumb links={breadCrumbLinks}/>
                <p className='text-3xl pt-4'>{product?.title}</p>
                <Rating ratings={product?.rating}/>
                {/* Price Tag */}
                <p className=' text-xl bold py-2'>${product?.price}</p>
                <div className='flex gap-2'>
                    <p className='text-sm bold'>Select Size</p>
                    <Link className='text-sm text-gray-500 hover:text-gray-900' to={'https://en.wikipedia.org/wiki/Clothing_sizes'} target='_blank'>{'Size Guide ->'}</Link>
                </div>

               <div className='mt-4'> <SizeFilter sizes={product?.size} hiddenTitle/> </div>
               <p className='text-lg bold'>Colors Available</p>
               <ProductColor colors={product?.color}/>
                
                <div className='flex py-5 mt-2'>
                    <button className='bg-black rounded-lg hover:bg-gray-700'>
                        <div className='flex text-white w-[150px]  items-center'>
                            <svg width="44" height="44" viewBox="0 0 44 44" fill="none" className='hover:fill-black'  xmlns="http://www.w3.org/2000/svg">
                            <path d="M14.5 15.3333H15.0053C15.8558 15.3333 16.5699 15.9737 16.6621 16.8192L17.3379 23.014C17.4301 23.8595 18.1442 24.4999 18.9947 24.4999H26.205C26.9669 24.4999 27.6317 23.9833 27.82 23.2451L28.9699 18.7358C29.2387 17.682 28.4425 16.6573 27.355 16.6573H17.5M17.5206 27.5207H18.1456M17.5206 28.1457H18.1456M26.6873 27.5207H27.3123M26.6873 28.1457H27.3123M18.6667 27.8333C18.6667 28.2935 18.2936 28.6666 17.8333 28.6666C17.3731 28.6666 17 28.2935 17 27.8333C17 27.373 17.3731 26.9999 17.8333 26.9999C18.2936 26.9999 18.6667 27.373 18.6667 27.8333ZM27.8333 27.8333C27.8333 28.2935 27.4602 28.6666 27 28.6666C26.5398 28.6666 26.1667 28.2935 26.1667 27.8333C26.1667 27.373 26.5398 26.9999 27 26.9999C27.4602 26.9999 27.8333 27.373 27.8333 27.8333Z" stroke={'white'} strokeWidth="1.5" strokeLinecap="round" />
                            </svg> Add to cart
                        </div>
                    
                    </button>
                </div>

                <div className='grid md:grid-cols-2 gap-4 pt-4'>
                    {
                        extraSections.map((section, index)=>(
                            <div className='flex items-center'>
                                {section?.icon}
                                <p className='px-2'>{section?.label}</p>
                            </div>
                        ))
                    }
                </div>
            </div>

        </div>
        {/* Product Description */}
        <div className='px-6 mt-10'><SeactionHeading title={'Product Description'}/></div>
        <div className='md:w-[50%] w-full p-2'>
            <p className='px-14'>{product.description}</p>
        </div>

        <div className='px-6 mt-10'><SeactionHeading title={'Similar Product'}/></div>
        <div className='flex px-14'>
            <div className='px-4 pt-4 pb-8 grid grid-cols-1 md:grid-cols-4 lg:grid-cols-3 gap-8'>
                    {similarProduct?.map((item, index)=>(
                        <ProductCard key={index} {...item}/>
                    ))}
                    {!similarProduct?.length && <p>No Products Found!</p>}
            </div>

        </div>

        
        </>
    )
}

export default ProductDetails