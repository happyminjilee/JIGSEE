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

export default function EngineerNavbar() {
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
    const result = await logout();
    if (result) {
      setName("");
      setRole("");
      console.log("delete zustand info");

      router.push("/login");
    } else {
      console.log("로그인이 만료되었습니다.");
      router.push("/login");
    }
  };
  // 현재위치 표시 로직
  const currentPath = router.pathname; // 현재 URL을 가져옵니다.

  // 페이지에 따라 스타일을 동적으로 적용합니다.
  const isRestorePage = currentPath === "/engineer/restore";
  const isReleasePage = currentPath === "/engineer";
  const isRepairPage = currentPath === "/engineer/repair";
  return (
    <>
      <Box style={{ paddingTop: "5px" }}>
        <List sx={{ display: "flex", paddingTop: "5px" }}>
          <ListItem>
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
            UserName
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
