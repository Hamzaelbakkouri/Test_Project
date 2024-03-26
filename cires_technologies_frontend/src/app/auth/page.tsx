'use client';
import React, { useEffect } from 'react';
import * as Auth from "@/Types/User"
import { SubmitHandler, useForm } from "react-hook-form"
import { useRouter } from 'next/navigation';
import Cookies from 'universal-cookie';
import Login from '@/RESOURCE/login';

const LoginForm = () => {
  const cookie = new Cookies();

  const { register, handleSubmit, formState: { errors, isLoading } } = useForm<Auth.RequestAuth>();
  const router = useRouter();
  useEffect(() => {
    (async () => {
      const result = await cookie.get("token");
      if (result != undefined)
        router.push("/");
    })();
  }, []);
  const onSubmit: SubmitHandler<Auth.RequestAuth> = async (credentils: Auth.RequestAuth) => {
    try {
      const result = (await Login(credentils)).data;
      console.log(result.token);
      document.cookie = `token=${result.token}; path=/`
      router.push("/");
    } catch (error: any) {
      alert("catch")
    }
  }

  return (
    <div>
      {/* <h2>Login</h2>
      <form >
        <div>
          <label>
            Email:
            <input  />
          </label>
        </div>
        <div>
          <label>
            Password:
            <input type="password"  />
            
          </label>
        </div>
        <button type="submit">Login</button>
      </form> */}

      <div className="h-screen flex">
        <div className="flex-1 flex flex-col justify-start py-12 px-4 sm:px-6 lg:flex-none lg:px-20 xl:px-24">
          <div className="w-full max-w-sm lg:w-96">
            <div>

              <h2 className="mt-6 text-3xl font-extrabold text-gray-900">Sign in to your account</h2>
            </div>

            <div className="mt-8">
              <div className="mt-6">
                <form action="#" method="POST" className="space-y-6" onSubmit={handleSubmit(onSubmit)}>
                  <div>
                    <label htmlFor="email" className="block text-sm font-medium text-gray-700">
                      Email address
                    </label>
                    <div className="mt-1">
                      <input
                        {...register("username", { required: "username is required" })}
                        id="email"
                        name="email"
                        type="email"
                        autoComplete="email"
                        className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-black focus:border-black sm:text-sm"
                      />
                      {errors.username && <p className="text-red-500 text-[12px]">{errors.username?.message}</p>}
                    </div>
                  </div>

                  <div className="space-y-1">
                    <label htmlFor="password" className="block text-sm font-medium text-gray-700">
                      Password
                    </label>
                    <div className="mt-1">
                      <input
                        {...register("password", { required: "password is required" })}
                        id="password"
                        name="password"
                        type="password"
                        autoComplete="current-password"
                        className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-black focus:border-black sm:text-sm"
                      />
                      {errors.password && <p className="text-red-500 text-[12px]">{errors.password?.message}</p>}
                    </div>
                  </div>

                  <div>
                    <button

                      type="submit"
                      className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
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
            src="https://images.unsplash.com/photo-1505904267569-f02eaeb45a4c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1908&q=80"
            alt=""
          />
        </div>
      </div>
    </div>
  );
};

export default LoginForm;
