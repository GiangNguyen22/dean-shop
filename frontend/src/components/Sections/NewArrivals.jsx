import Card from '../Card/Card'
import SectionHeading from './SectionHeading/SeactionHeading'
import Jeans from "../../assets/image/jeans.jpg"
import Dresses from "../../assets/image/dresses.jpg"
import Shirts from "../../assets/image/shirts.jpg"
import Tshirt from "../../assets/image/tshirts.jpeg"
import Carousel from 'react-multi-carousel'
import { responsive } from '../../utils/Section.constant'
import './NewArrivals.css'

const items = [
    {
        title: "Jeans",
        imagePath: Jeans
    },
    {
        title: "Dresses",
        imagePath: Dresses
    },
    {
        title: "Shirts",
        imagePath: Shirts
    },
    {
        title: "TShirt",
        imagePath: Tshirt
    },
    {
        title:"Joggers",
        imagePath: require('../../assets/image/joggers.jpg')
    },
    {
        title:"Kurtis",
        imagePath: require('../../assets/image/kurtis.jpg')
    },
    {
        title:"Blazer",
        imagePath: require('../../assets/image/blazer.jpg')
    }
    
]

const NewArrivals = () => {
  return (
    <>
        <SectionHeading title={'New Arrivals'}/>
        {/* <div className='flex flex-wrap px-[20px]'> */}
        <Carousel 
             responsive={responsive}
             autoPlay={false}
             swipeable={true}
             draggable={false}
             showDots={false}
             infinite={false}
             partialVisible={false}
             itemClass={'react-slider-custom-item'}
            //  thẻ bao ngoài cùng của Carousel
             className='px-8'
        >
            
            {items && items.map((item, index) => <Card key={item.title+index} title={item.title} imagePath={item.imagePath}/>)}
        </Carousel>
        {/* </div> */}
    </>
  )
}

export default NewArrivals