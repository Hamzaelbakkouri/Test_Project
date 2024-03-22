'use client'
import Navbar from "@/components/user/Navbar";
import { LandingTitle } from "@/components/Landing/LandingTitle";
import { Globe } from "@/components/Globe";
export default function Home() {
  return (
    <main className="flex flex-col h-screen">
      <Navbar />
    </main>
  );
}