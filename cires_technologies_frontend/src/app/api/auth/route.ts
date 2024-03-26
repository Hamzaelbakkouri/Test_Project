import { NextRequest, NextResponse } from "next/server";
import { Level } from 'level';
export const db = new Level('cires', { valueEncoding: 'json' })
import jwt from 'jsonwebtoken'

interface Request {
    username: string,
    password: string
}

export const POST = async (request: NextRequest) => {
    const body = await request.json();
    var role: number = 0;

    if (body.username == "muser1" && body.password == "mpassword1") {
        return NextResponse.json({
            token: jwt.sign(body.username, process.env.JWT_SECRET as string, {
                expiresIn: "10h"
            })
        }, { status: 200 })

    }
    else if (body.username == "muser2" && body.password == "mpassword2") {
        return NextResponse.json({ message: "This account has been blocked", status: 403 });

    }
    else if (body.username == "muser3" && body.password == "mpassword3") {
        return NextResponse.json({ message: "account or password and invalid", status: 404 });

    }
    else
        return Response.json({ message: "users added successfully" });
}

export const GET = () => {
    const users = db.get('users');
    return Response.json(users);
}