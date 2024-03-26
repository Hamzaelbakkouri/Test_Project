"use client";
import React, { useEffect, useState } from "react";
import { BentoGrid, BentoGridItem } from "@/components/bento-grid";
import axios from "axios";
import Cookies from 'universal-cookie';

export default function page() {
  const [paginate, setPaginate] = useState(10);
  const [page, setPage] = useState(1);
  const [data, setData] = useState<any>([]);
  

  useEffect(() => {
    (async () => {
      await axios
        .get(`https://api.unsplash.com/photos?page=${page}&per_page=${paginate}&client_id=VP-S8p1Wj8Cq9d8EsR_APHbEm53eEgYMLqKS4mlf0a0`)
        .then((response) => {
          setData(response.data);
        })
        .catch((error) => {
          console.error("Error fetching data:", error.message);
        });
    })()
  }, [paginate]);

  return (
    <div className="w-full bg-[#1E1F24] flex gap-5 flex-wrap justify-center">
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
            Liked={item.liked_by_user}
          />
        ))}
      </BentoGrid>
    </div>
  );
}

// const Skeleton = () => (
//   <div className="flex flex-1 w-full h-full min-h-[6rem] rounded-xl bg-gradient-to-br from-neutral-200 dark:from-neutral-900 dark:to-neutral-800 to-neutral-100"></div>
// );