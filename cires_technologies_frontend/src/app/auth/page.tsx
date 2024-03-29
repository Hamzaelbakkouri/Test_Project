'use client';
import React, { useEffect } from 'react';
import * as Auth from "@/Types/User"
import { SubmitHandler, useForm } from "react-hook-form"
import { useRouter } from 'next/navigation';
import { toast } from "react-toastify";
import Cookies from 'universal-cookie';
import { Login } from '@/API/login';

const page = () => {
  const cookie = new Cookies();

  const { register, handleSubmit, formState: { errors, isLoading } } = useForm<Auth.RequestAuth>();
  const router = useRouter();
  useEffect(() => {
    const result = cookie.get("token");
    if (result)
      router.push("/blog");
  }, []);

  const onSubmit: SubmitHandler<Auth.RequestAuth> = async (credentils: Auth.RequestAuth, e: any) => {
    e.preventDefault();
    try {
      const result = (await Login(credentils)).data;
      if (result.token) {
        cookie.set("token", result.token);
        if (cookie.get("token")) {
          router.push("/blog");
        }
      }
    } catch (error: any) {
      alert("Bad Credentials");
    }
  }

  return (
    <div>
      <div className="h-screen flex">
        <div className="flex-1 flex flex-col bg-[#1E1F24] justify-start py-12 px-4 sm:px-6 lg:flex-none lg:px-20 xl:px-24">
          <div className="w-full max-w-sm lg:w-96 ">
            <div>
              <h2 className="mt-6 text-3xl font-extrabold text-white">Sign in to your account</h2>
            </div>
            <div className="mt-8">
              <div className="mt-6">
                <form className="space-y-6" onSubmit={handleSubmit(onSubmit)}>
                  <div>
                    <label htmlFor="email" className="block text-sm font-medium text-white">
                      Email address
                    </label>
                    <div className="mt-1">
                      <input
                        {...register("username", { required: "username is required" })}
                        id="username"
                        name="username"
                        type="text"
                        className="appearance-none block w-full px-3 py-2 border text-black border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-black focus:border-black sm:text-sm"
                      />
                      {errors.username && <p className="text-red-500 text-[12px]">{errors.username?.message}</p>}
                    </div>
                  </div>

                  <div className="space-y-1">
                    <label htmlFor="password" className="block text-sm font-medium text-white">
                      Password
                    </label>
                    <div className="mt-1">
                      <input
                        {...register("password", { required: "password is required" })}
                        id="password"
                        name="password"
                        type="password"
                        autoComplete="current-password"
                        className="appearance-none block w-full px-3 py-2 border text-black border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-black focus:border-black sm:text-sm"
                      />
                      {errors.password && <p className="text-red-500 text-[12px]">{errors.password?.message}</p>}
                    </div>
                  </div>

                  <div>
                    <button

                      type="submit"
                      className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-blue-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                    >
                      Sign in
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
        <div className="hidden lg:block relative w-0 flex-1">
          <img
            className="absolute inset-0 h-full w-full object-cover"
            src="https://images.pexels.com/photos/861443/pexels-photo-861443.jpeg?auto=compress&cs=tinysrgb&w200"
            alt=""
          />
        </div>
      </div>
    </div>
  );
};

export default page;
