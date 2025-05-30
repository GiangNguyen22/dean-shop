import { AccountIcon } from "./../common/AccountIcon"
import { Wishlist } from "./../common/Wishlist"
import { CartIcon } from "./../common/CartIcon"
import { NavLink } from "react-router-dom"
import './Navigation.css'

export default function Navigation({ variant = "default" }) {
    return (
        <nav className="flex items-center py-6 px-16 justify-between gap-44 custom-nav">
            <div className="flex items-center gap-6">
                {/* logo */}
                <a href="/" className="text-3xl text-black font-bold">Dean Shop</a>
            </div>
            {variant === "default" &&
                <div className="flex flex-wrap items-center gap-10 flex-1">
                    {/* Nav item */}
                    <ul className="flex gap-14 text-gray-600 hover:text-black">
                        <li><NavLink to='/' className={({ isActive }) => isActive ? 'active-link' : ''}>Shop</NavLink></li>
                        <li><NavLink to='/men' className={({ isActive }) => isActive ? 'active-link' : ''}>Men</NavLink></li>
                        <li><NavLink to='/women' className={({ isActive }) => isActive ? 'active-link' : ''}>Women</NavLink></li>
                        <li><NavLink to='/kids' className={({ isActive }) => isActive ? 'active-link' : ''}>Kids</NavLink></li>
                    </ul>
                </div>
            }

            {variant === "default" &&
                <div>
                    {/* Search bar */}
                    <div className="border rounded items-center justify-center flex px-4 overflow-hidden ">
                        <svg className="w-4 text-grey-dark" fill="currentColor" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"><path d="M16.32 14.9l5.39 5.4a1 1 0 0 1-1.42 1.4l-5.38-5.38a8 8 0 1 1 1.41-1.41zM10 16a6 6 0 1 0 0-12 6 6 0 0 0 0 12z" /></svg>
                        <input type="text" className="px-4 py-2 outline-none" placeholder="Search" />
                    </div>
                </div>
            }

            <div className="flex flex-wrap items-center gap-4">
                {/* Action Item-icons */}
                {variant === "default" &&
                    <ul className="flex gap-8">
                        <li><button><Wishlist /></button></li>
                        <li><button><AccountIcon /></button></li>
                        <li><a href="/"><CartIcon /></a></li>
                    </ul>
                }

                {
                    variant === "auth" &&
                    <ul className='flex gap-8'>
                        <li className='text-black border border-black hover:bg-slate-100 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 focus:outline-none'><NavLink to={"/v1/login"} className={({ isActive }) => isActive ? 'active-link' : ''}>Login</NavLink></li>
                        <li className='text-black border border-black hover:bg-slate-100 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 focus:outline-none'><NavLink to="/v1/register" className={({ isActive }) => isActive ? 'active-link' : ''}>Signup</NavLink></li>
                    </ul>
                }
            </div>


        </nav>
    )
}