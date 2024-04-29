import { Input, Button, Checkbox } from "@nextui-org/react";
import React,{useState} from "react";
import styled from "@/styles/login.module.scss";
import {useRouter} from "next/router"
import {login} from "@/pages/api/memberAxios"
import {userStore} from "@/store/memberstore";



export default function Login() {
  const [employeeNo, setEmployeeNo] = useState("")
  const [password, setPassword ]= useState("")
  const router = useRouter()
  const { setName, setRole } = userStore(state => ({
      setName: state.setName,
      setRole: state.setRole
  }))

    const handlelogin = async (p1:string, p2:string) => {
      const result = await login(p1, p2);
      if (result.success) {
          setName(result.name);
          setRole(result.role);
          console.log('what you want')
      } else {
          console.log('Login failed')
      }
  }
  return (
    <>
      <div className={styled.container}>
        <div className={styled.logincontainer}>
          <img
            src="/images/userprofile.svg"
            alt="profile-logo"
            style={{ width: "50px", height: "50px" }}
          />
          {/* 로그인 인풋 영역 */}
          <div style={{ marginBottom: "1rem" }}>
            <Input
                type="id"
                variant={"underlined"}
                label="사번"
                onChange={(e) => setEmployeeNo(e.target.value)}/>
          </div>
          <div style={{ marginBottom: "1rem" }}>
            <Input
                type="password"
                variant={"underlined"}
                label="비밀번호"
                onChange={(e) => setPassword(e.target.value)}
            />
          </div>
          <div style={{ marginBottom: "2rem", justifyContent: "flex-end", display: "flex" }}>
            <Checkbox defaultSelected>아이디 저장</Checkbox>
          </div>
          <Button
              className={styled.samsungbutton}
              onPress={(e) => handlelogin(employeeNo, password)}
          >
            로그인</Button>
        </div>
      </div>
    </>
  );
}
