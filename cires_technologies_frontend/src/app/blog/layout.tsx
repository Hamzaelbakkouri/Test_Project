'use client'
import Navbar from "@/components/Navbar";

export default function blogLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html lang="en">
            <body>
                <div className="w-full h-screen ">
                    <Navbar />
                    <div>
                        {children}
                    </div>
                </div>
            </body>
        </html>
    );
}