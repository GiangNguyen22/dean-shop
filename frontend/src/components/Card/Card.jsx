import ArrowIcon from "../common/ArrowIcon"

const Card = ({imagePath, title, description, actionArrow}) => {
  return (
    <div className="flex flex-col p-8">
        <img className="max-w-[240px] max-h-[260px] min-h-[260px] min-w-[220px] border rounded bg-cover bg-center
            hover:scale-105 cursor-pointer" src={imagePath} alt={title} />
        
        <div className="flex justify-between items-center">
          <div className="flex flex-col">
            <p className="text-[16px]">{title}</p>
            {description && <p className="text-gray-500 text-[12px] px-1">{description}</p>}
          </div>
          {actionArrow && <span className="cursor-pointer pr-2 pb-2 items-center"><ArrowIcon/></span>}
        </div>
        
        
    </div>
  )
}

export default Card