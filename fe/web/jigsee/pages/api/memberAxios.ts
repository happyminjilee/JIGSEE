import {axiosApi, axiosAuthApi} from '@/utils/instance';
import {userStore} from "@/store/memberstore";



export const login = async (employeeNo:string, password:string) => {
    console.log('Want to login?')
    const http = axiosApi()

    return await http
        .post('/login',
            {
            employeeNo: employeeNo,
            password: password,
        })
        .then((response) => {            
            localStorage.setItem('access_token', response.headers['authorization'])
            localStorage.setItem('refresh_token', response.headers['refreshToken'])
            console.log('response login', response.headers)
            return {
                success: true,
                name: response.data.result.name,
                role: response.data.result.role
            }
        })
        .catch((error) => {
            console.log('login failed', error.message);
            return {
                success: false,
                name: "",
                role: "",
            }
        })
}


export const logout = async () => {
    console.log('want to logout?')
    const request = axiosAuthApi()
    const accessToken = localStorage.getItem('access_token')
    return await request
        .post(
            '/member/logout'
        )
        // 로그아웃 성공 시 local storage 삭제
        .then((response) => {
            localStorage.removeItem('access_token')
            localStorage.removeItem('refresh_token')
            return true
        })
        // 토큰 만료 시 어떻게 해야하는거..?
        // local storage 삭제해야하나?
        .catch((error) => {
            return false
        })
}