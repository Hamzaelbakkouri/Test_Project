
export default function UserLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    return (
        <html lang="en">
            <body>
                <div className="w-full h-screen">
                    <div className="">
                        {children}
                    </div>
                </div>
            </body>
        </html>
    );
}