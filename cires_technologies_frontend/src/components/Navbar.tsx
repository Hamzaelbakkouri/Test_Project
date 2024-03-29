'use client'
import Link from 'next/link';
import React, { useState } from 'react'
import { GoHomeFill } from "react-icons/go";
import Cookies from 'universal-cookie';
import { useRouter } from 'next/navigation';


const Navbar = () => {
    const cookie = new Cookies();
    const router = useRouter();
    const [isLogged, setLogged] = useState(cookie.get('token'));
    const handleLogout = () => {
        cookie.remove("token");
        setLogged(null);
        router.push('/auth');
    }
    return (
        <>
            <div className='w-full bg-[#1E1F24] h-[9%] border border-l-gray-900 p-4 border-r-gray-900 border-t-gray-900 border-b-gray-600 flex justify-between items-center'>
                {/* First section */}
                <div className='flex justify-between items-center w-[50%]'>
                    <div className='flex items-center'>
                        <GoHomeFill className='text-gray-300 text-2xl ml-4' />
                        <p className='text-gray-200 text-lg ml-2'>Home</p>
                    </div>
                </div>

                {/* seconds section */}
                <div className='w-[50%] flex justify-end items-center'>
                    <div className='flex gap-3'>

                        {
                            isLogged ?
                                <>
                                    <Link href="/blog" className='bg-[#27292F] hover:bg-[#33353D] hover:text-[#45a3fce3] border border-gray-600 rounded-md py-2 px-7 flex text-sm'>
                                        <p>Blogs</p>
                                    </Link>
                                    <button onClick={handleLogout} className='bg-[#27292F] hover:bg-[#33353D] hover:text-[#45a3fce3] border border-gray-600 rounded-md py-2 px-7 flex text-sm'>
                                        <p>Logout</p>
                                    </button>
                                </>
                                :
                                <Link href="/auth" className='bg-[#27292F] hover:bg-[#33353D] hover:text-[#45a3fce3] border border-gray-600 rounded-md py-2 px-7 flex text-sm'>
                                    <p>Auth</p>
                                </Link>
                        }
                    </div>
                </div>
            </div>
        </>
    )
}

export default Navbar
