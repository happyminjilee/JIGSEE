import axios, {AxiosInstance} from 'axios';
import {headers} from "next/headers";


const api = 'https://www.k8s105.p.ssafy.io/api/v1'

const axiosAuthApi = (): AxiosInstance => {
    const accessToken = localStorage.getItem('access_token');
    const refreshToken = localStorage.getItem('refresh_token');
    const instance = axios.create({
        baseURL: api,
        headers: {Authorization: 'Bearer ' + accessToken},
    });

    instance.interceptors.response.use(
        (response) => {
            return response;
        },
        async (error) => {
            const {
                config,
                response: {status},
            } = error;

            if (error.response?.resultCode === "EXPIRED_ACCESS_TOKEN") {
                const originRequest = config;
                // 무한 재시도 방지
                if (!originRequest._retry) {
                    originRequest._retry = true;

                    try {
                        const response =
                            await axiosAuthApi().put(
                                '/refresh',
                                {},
                                {
                                    headers: {
                                        RefreshToken: 'Bearer ' + refreshToken
                                    }
                                });
                        const newAccessToken = response.headers['authorization']
                        localStorage.setItem('access_token', newAccessToken);
                        originRequest.headers.Authorization = `Bearer ${newAccessToken}`;
                        return axios(originRequest)
                    } catch (refreshError) {
                        window.alert('로그인이 만료되었습니다.');
                        // 페이지 리다이렉트
                    }
                }
            }
            if (error.response?.resultCode === "INVALID_ACCESS_TOKEN") {
                window.alert('로그인이 만료되었습니다.');
            }

            return Promise.reject(error);
        }
    );
    return instance;
};

const axiosApi = (): AxiosInstance => {
    return axios.create({
        baseURL: api,
    })
};