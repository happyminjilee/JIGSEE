"use client";

import axios, { AxiosInstance } from "axios";
import { headers } from "next/headers";
import { setCookie, eraseCookie } from "@/utils/cookie";

const api = process.env.NEXT_PUBLIC_API_URI;

const axiosAuthApi = (): AxiosInstance => {
  const accessToken = localStorage.getItem("access_token");
  const instance = axios.create({
    baseURL: api,
    headers: { Authorization: accessToken },
  });

  instance.interceptors.response.use(
    (response) => {
      return response;
    },
    async (error) => {
      const {
        config,
        response: { status },
      } = error;

      console.log("!!!!!!!!!!", error.response.data.resultCode);
      console.log(error.response);
      if (error.response.data.resultCode === "EXPIRED_ACCESS_TOKEN") {
        console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        const originRequest = config;
        // 무한 재시도 방지
        if (!originRequest._retry) {
          originRequest._retry = true;

          try {
            const refreshToken = localStorage.getItem("refresh_token");
            const response = await axiosAuthApi().post(
              "/refresh",
              {},
              {
                headers: {
                  RefreshToken: refreshToken,
                },
              }
            );
            eraseCookie("refresh_token");
            const newAccessToken = response.headers["authorization"];
            const newRefreshToken = response.headers["refreshtoken"];
            console.log("newAccessToken", newAccessToken);
            console.log("newRefreshToken", newRefreshToken);
            localStorage.setItem("access_token", newAccessToken);
            localStorage.setItem("refresh_token", newRefreshToken);
            setCookie("refresh_token", newRefreshToken, 7);
            console.log("local access", localStorage.getItem("access_token"));
            console.log("local refresh", localStorage.getItem("refresh_token"));
            originRequest.headers.Authorization = newAccessToken;
            // originRequest.headers.RefreshToken = newRefreshToken;
            console.log("originRequest", originRequest);
            console.log(
              "orginRequestToken",
              originRequest.headers.Authorization
            );
            console.log(
              "originRequestRefresh",
              originRequest.headers.RefreshToken
            );
            return axios(originRequest);
          } catch (refreshError) {
            localStorage.removeItem("access_token");
            localStorage.removeItem("refresh_token");
            // localStorage.setItem('refresh_token', "")
            window.alert("로그인이 만료되었습니다.");
          }
        }
      }
      if (error.response.data.resultCode === "INVALID_ACCESS_TOKEN") {
        window.alert(" Invalid Access Token");
      }

      return Promise.reject(error);
    }
  );
  return instance;
};

const axiosApi = (): AxiosInstance => {
  return axios.create({
    baseURL: api,
  });
};

export { axiosApi, axiosAuthApi };
