import {
  Box,
  List,
  ListItem,
  Button,
  Avatar,
  IconButton,
  MenuItem,
  Menu,
  Typography,
} from "@mui/material";
import * as React from "react";
import { useRouter } from "next/router";
import { logout } from "@/pages/api/memberAxios";
import { userStore } from "@/store/memberstore";
import { useEffect } from "react";
import Badge from "@mui/material/Badge";
import { useAlarmStore } from "@/store/ssestore";
import { finishSSE } from "@/pages/api/sseAxios";
export default function EngineerNavbar() {
  // 알람 스토어 변수 들
  const { uncheckednumber, setUnchecked } = useAlarmStore();

  // profile logo 선택 메뉴 보기 핸들러
  const [anchorElNav, setAnchorElNav] = React.useState<null | HTMLElement>(null);
  const [anchorElUser, setAnchorElUser] = React.useState<null | HTMLElement>(null);
  const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };
  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };
  const router = useRouter();
  const { setName, setRole, name, role } = userStore((state) => ({
    name: state.name,
    role: state.role,
    setName: state.setName,
    setRole: state.setRole,
  }));
  useEffect(() => {
    console.log("Updated name:", name);
    console.log("Updated role:", role);
  }, [name, role]);
  const handlelogout = async () => {
    //로그아웃 해제
    finishSSE();
    const result = await logout();
    if (result) {
      setName("");
      setRole("");

      console.log("delete zustand info");

      router.push("/login");
    } else {
      console.log("로그인이 만료되었습니다.");
      router.push("/login");
    } // 알림 get test
  };
  const [UserName, setUsername] = React.useState("");
  // 알림 get test, userNAme 가져오기
  useEffect(() => {
    setUnchecked();
    const storedName = localStorage.getItem("name");
    setUsername(storedName ?? ""); // localStorage에서 값이 null이면 빈 문자열을 사용
  }, []);
  // 현재위치 표시 로직
  // 현재위치 표시 로직
  const currentPath = router.pathname; // 현재 URL을 가져옵니다.

  // 페이지에 따라 스타일을 동적으로 적용합니다.
  const isRestorePage = currentPath === "/engineer/restore";
  const isReleasePage = currentPath === "/engineer";
  const isRepairPage = currentPath === "/engineer/repair";
  // 알람 보기로 이동
  const navigateToAlarm = () => {
    router.push("/alarm");
  };
  const navigateToDashboard = () => {
    router.push("/dashboard/engineer");
  };
  return (
    <>
      <Box style={{ paddingTop: "5px" }}>
        <List sx={{ display: "flex", paddingTop: "5px" }}>
          <ListItem onClick={navigateToDashboard}>
            <img
              src="/images/sdi-logo.svg"
              alt="Logo"
              style={{
                height: "60px",
                marginTop: "-10px",
                marginLeft: "40px",
                cursor: "pointer",
              }}
            />
          </ListItem>
          <ListItem
            sx={{
              paddingTop: "10px",
              paddingBottom: "0px",
              width: "70%",
              paddingLeft: "180px",
            }}
          >
            <Button
              sx={{
                fontSize: "20px",
                color: "#40404A",
                fontWeight: "bold",
                width: "100%",
                textDecoration: isRestorePage ? "underline var(--samsungblue)" : "none",
              }}
              href="/engineer/restore"
            >
              보수 요청
            </Button>
          </ListItem>
          <ListItem sx={{ paddingTop: "10px", paddingBottom: "0px", width: "95%" }}>
            <Button
              sx={{
                fontSize: "20px",
                color: "#40404A",
                fontWeight: "bold",
                width: "100%",
                textDecoration: isReleasePage ? "underline var(--samsungblue)" : "none",
              }}
              href="/engineer"
            >
              Jig 불출
            </Button>
          </ListItem>
          <ListItem sx={{ paddingTop: "10px", paddingBottom: "0px" }}>
            <Button
              sx={{
                fontSize: "20px",
                color: "#40404A",
                fontWeight: "bold",
                width: "100%",
                marginRight: "50px",
                textDecoration: isRepairPage ? "underline var(--samsungblue)" : "none",
              }}
              href="/engineer/repair"
            >
              Jig 수리
            </Button>
          </ListItem>

          <ListItem sx={{ paddingTop: "10px", paddingBottom: "0px" }}>
            <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
              <Avatar alt="Remy Sharp" src="/images/userprofile.svg" />
            </IconButton>
            {UserName}
            <Badge badgeContent={uncheckednumber} color="primary">
              <IconButton onClick={navigateToAlarm} sx={{ p: 0, marginLeft: "10px" }}>
                <Avatar
                  alt="Remy Sharp"
                  src="/images/bell.svg"
                  sx={{ width: "30px", height: "30px" }}
                />
              </IconButton>
            </Badge>
          </ListItem>

          <Menu
            sx={{ mt: "45px" }}
            id="menu-appbar"
            anchorEl={anchorElUser}
            anchorOrigin={{
              vertical: "top",
              horizontal: "right",
            }}
            keepMounted
            transformOrigin={{
              vertical: "top",
              horizontal: "right",
            }}
            open={Boolean(anchorElUser)}
            onClose={handleCloseUserMenu}
          >
            <MenuItem onClick={handleCloseUserMenu}>
              <Typography textAlign="center" onClick={handlelogout}>
                Logout
              </Typography>
            </MenuItem>
          </Menu>
        </List>
      </Box>
    </>
  );
}
