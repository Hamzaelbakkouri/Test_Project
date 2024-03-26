import * as Auth from "@/Types/User"
import axios from "axios"

export function Login(credientls: Auth.RequestAuth) {
    return axios.post<Auth.ResponseAuth>("/api/login", credientls);
}
