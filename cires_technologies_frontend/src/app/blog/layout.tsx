'use client'
import { useEffect, useState } from "react";
import Cookies from "universal-cookie";

export default function blogLayout({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {
    const [logged, setLogged] = useState<boolean>(false);

    const cookie = new Cookies();
    useEffect(() => {
        (async () => {
            if (await cookie.get("token") != undefined)
                setLogged(true);
        })();
    }, []);

    const handleLogout = () => {
        cookie.remove("token");
        setLogged(false);
    }
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