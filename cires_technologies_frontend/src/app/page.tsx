'use client'
import Navbar from "@/components/Navbar";
// import 
import { Globe } from "@/components/user/globe";

export default function Home() {
  return (
    <div className="bg-[#1E1F24]">
      <Navbar />  
      <Globe />
    </div>
  );
}