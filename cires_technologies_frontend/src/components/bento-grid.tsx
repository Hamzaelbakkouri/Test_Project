'use client';
import { cn } from "@/utils/cn";
import { StaticImport } from "next/dist/shared/lib/get-img-props";
import Image from "next/image";
import { IoMdHeart } from "react-icons/io";
import { useState } from "react";
import axios from "axios";

export const BentoGrid = ({
    className,
    children,
}: {
    className?: string;
    children?: React.ReactNode;
}) => {
    return (
        <div
            className={cn(
                "grid grid-cols-1 md:grid-cols-3 gap-8 max-w-10xl ",
                className
            )}
        >
            {children}
        </div>
    );
};

export const BentoGridItem = ({
    id,
    className,
    title,
    description,
    header,
    icon,
    Liked
}: {
    id: string;
    className?: string;
    title?: string | React.ReactNode;
    description?: string | React.ReactNode;
    header?: string | StaticImport;
    icon?: React.ReactNode;
    Liked?: boolean;
}) => {
    const [like, setLike] = useState(Liked);

    return (
        <div
            className={cn(
                "row-span-1 rounded-xl group/bento hover:shadow-xl transition duration-200 shadow-input dark:shadow-none p-4 dark:bg-black dark:border-white/[0.2] bg-white border border-transparent justify-between flex flex-col w-[100%] h-[105%] space-y-2",
                className
            )}
        >
            <div className="w-full h-full">
                {/* @ts-ignore */}
                <Image src={header} alt="..." width={250} height={30} className="text-neutral-500 h-48 w-full object-cover" />
            </div>
            <div className="group-hover/bento:translate-x-1 transition duration-200">
                {icon}
                <div className="font-sans font-bold text-neutral-600 dark:text-neutral-200 mb-2 mt-2">
                    {title}
                </div>
                <div className="font-sans font-normal text-neutral-600 text-xs dark:text-neutral-300">
                    {description}
                </div>
                <div className="flex justify-end items-center">
                    {like ? (
                        <IoMdHeart className="text-red-500 text-3xl hover:text-4xl transition-all duration-200 cursor-pointer" onClick={() => setLike(!like)} />
                    ) :
                        <IoMdHeart className="text-white text-3xl hover:text-4xl transition-all duration-200 cursor-pointer" onClick={() => setLike(!like)} />
                    }
                </div>
            </div>
        </div>
    );
};
