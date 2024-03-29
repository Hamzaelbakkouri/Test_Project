'use client'
import React, { useEffect, useState } from "react";
import { BentoGrid, BentoGridItem } from "@/components/bento-grid";
import axios from "axios";
import { Level } from "level";
import Cookies from 'universal-cookie';
import { useRouter } from "next/navigation";

export default function page() {
  const db = new Level("likes", { valueEncoding: "json" });
  const [paginate, setPaginate] = useState(10);
  const [page, setPage] = useState(1);
  const [data, setData] = useState<any>([]);
  const [likedPosts, setLikedPosts] = useState<any>([]);
  const [cookies, setCookies] = useState('' as any);
  const cookie = new Cookies();
  const router = useRouter();


  useEffect(() => {
    setCookies(cookie.get('token'));
    if (!cookies) {
      router.push('/');
    }
    loadLikedPosts();

    fetchData();
  }, [paginate, page]);

  const loadLikedPosts = async () => {
    try {
      const likedPosts = await db.get("likedPosts");
      setLikedPosts(likedPosts);
    } catch (error: any) {
      if (error.notFound) {
        console.log("No liked posts found.");
      } else {
        console.error("Error loading liked posts:", error);
      }
    }
  };

  const saveLikedPosts = async () => {
    try {
      await db.put("likedPosts", likedPosts);
    } catch (error) {
      console.error("Error saving liked posts:", error);
    }
  };

  const fetchData = async () => {
    try {
      const response = await axios.get(`https://api.unsplash.com/photos?page=${page}&per_page=${paginate}&client_id=VP-S8p1Wj8Cq9d8EsR_APHbEm53eEgYMLqKS4mlf0a0`);
      setData(response.data);
    } catch (error: any) {
      console.error("Error fetching data:", error.message);
    }
  };

  useEffect(() => {
    saveLikedPosts();
  }, [likedPosts]);

  return (
    <div className="bg-[#1E1F24]">
      <div className="w-full flex gap-5 flex-wrap justify-center">
        <BentoGrid className="max-w-4xl mx-auto p-4">
          {data?.map((item: any, i: number) => (
            <BentoGridItem
              id={item.id}
              key={i}
              title={item.slug}
              description={item.description}
              header={item.urls.regular}
              icon={item.icon}
              className={i === 3 || i === 6 ? "md:col-span-2 " : ""}
              Liked={likedPosts.includes(item.id)}
            />
          ))}
        </BentoGrid>
      </div>
      <div className="w-full h-full flex py-10 justify-center gap-4">
        <button onClick={() => { setPage(page - 1) }} className="bg-[#000000] flex justify-center items-center py-3 px-5 rounded-md text-2xl">{'<'}</button>
        <button onClick={() => { setPage(page + 1) }} className="bg-[#000000] flex justify-center items-center py-3 px-5 rounded-md text-2xl">{'>'}</button>
      </div>
    </div>
  );
}
