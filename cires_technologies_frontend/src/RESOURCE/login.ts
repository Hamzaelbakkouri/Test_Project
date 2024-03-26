import * as Auth from "@/Types/User"
import axios from "axios"

export default function Login(credientls: Auth.RequestAuth) {
    return axios.post<Auth.ResponseAuth>("/api/login", credientls);
}
