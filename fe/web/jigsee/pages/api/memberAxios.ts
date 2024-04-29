import {axiosApi, axiosAuthApi} from '@/utils/instance';
import {userStore} from "@/store/memberstore";



export const login = async (employeeNo:string, password:string) => {
    console.log('Want to login?')
    const http = axiosApi()

    return await http
        .post('/login', {
            employeeNo: employeeNo,
            password: password,
        })
        .then((response) => {
            console.log("hi")
            console.log(response)
            localStorage.setItem('access_token', response.headers['access_token'])
            localStorage.setItem('refresh_token', response.headers['refresh_token'])
            console.log('response login', response)
            return {
                success: true,
                name: response.data.result.name,
                role: response.data.result.role
            }
        })
        .catch((error) => {
            console.log(error.message);
            return {
                success: false,
                name: "",
                role: "",
            }
        })
}