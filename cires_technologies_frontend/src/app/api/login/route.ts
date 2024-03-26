import { NextRequest, NextResponse } from "next/server";
import Jwt from 'jsonwebtoken'

export async function POST(request: NextRequest) {
    const body = await request.json();
    const JWT_SECRET = "2018"

    if (body.username == "muser1" && body.password == "mpassword1") {
        const payload = {
            username: body.username
        };

        const token = await Jwt.sign(payload, JWT_SECRET as string, {
            expiresIn: "15h"
        })

        return NextResponse.json({
            token: token
        }, { status: 200 });

    } else if (body.username == "muser2" && body.password == "mpassword2") {
        return NextResponse.json({ message: "This account has been blocked", status: 403 });

    } else if (body.username == "muser3" && body.password == "mpassword3") {
        return NextResponse.json({ message: "account or password and invalid", status: 404 });

    } else {
        return NextResponse.json({ message: "users added successfully" });
    }
}
