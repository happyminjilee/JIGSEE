import { eraseCookie, setCookie } from "@/utils/cookie";
import { axiosApi, axiosAuthApi } from "@/utils/instance";

export const login = async (employeeNo: string, password: string) => {
  console.log("Want to login?");
  const http = axiosApi();

  return await http
    .post(
      "/login",
      {
        employeeNo: employeeNo,
        password: password,
      },
      {}
    )
    .then((response) => {
      localStorage.setItem("access_token", response.headers["authorization"]);
      localStorage.setItem("refresh_token", response.headers["refreshtoken"]);
      setCookie("refresh_token", response.headers["refreshtoken"], 7);
      setCookie("role", response.data.result.role.toLowerCase(), 7);
      // console.log('response login', response.headers)
      // console.log(localStorage.getItem('refresh_token'))
      return {
        success: true,
        name: response.data.result.name,
        role: response.data.result.role,
      };
    })
    .catch((error) => {
      console.log("login failed", error.message);
      eraseCookie("refresh_token");
      localStorage.removeItem("access_token");
      localStorage.removeItem("refresh_token");
      return {
        success: false,
        name: "",
        role: "",
      };
    });
};

export const logout = async () => {
  console.log("want to logout?");
  const request = axiosAuthApi();
  return await request
    .post("/logout", {}, {})
    // 로그아웃 성공 시 local storage 삭제
    .then((response) => {
      eraseCookie("refresh_token");
      eraseCookie("role");
      localStorage.removeItem("access_token");
      localStorage.removeItem("refresh_token");
      return true;
    })
    // 토큰 만료 시 어떻게 해야하는거..?
    // local storage 삭제해야하나?
    .catch((error) => {
      eraseCookie("refresh_token");
      localStorage.removeItem("access_token");
      localStorage.removeItem("refresh_token");
      return false;
    });
};

export const searchUser = async () => {
  const request = axiosAuthApi();
  return await request
    .get("/member/search", {})
    .then((res) => {
      return res.data.result;
    })
    .catch((error) => {
      console.log(error.message);
    });
};
