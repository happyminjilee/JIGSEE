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
import MoreVertIcon from "@mui/icons-material/MoreVert";
import { finishSSE, getUnchecked, getAllalarms } from "@/pages/api/sseAxios";
// 알람 리스트 api 연결 필요
const options = [
  "불출요청:121354",
  "수리요청:2354",
  "보수요청:12345",
  "보수요청:5546",
  // "수리요청:882354",
  // "불출요청:786412",
  // "불출요청:354456",
  // "수리요청:03754",
  // "수리요청:8354",
  // "보수요청:32245",
];
const ITEM_HEIGHT = 48;
export default function EngineerNavbar() {
  // 알람 리스트 보기 핸들러
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
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
    const result = await logout();
    if (result) {
      setName("");
      setRole("");
      console.log("delete zustand info");
      finishSSE();
      router.push("/login");
    } else {
      console.log("로그인이 만료되었습니다.");
      router.push("/login");
    }
  };
  // 알림 get test
  useEffect(() => {
    getUnchecked()
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.error(error);
      });
    getAllalarms()
      .then((response) => {
        console.log("전체리스트", response);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);
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
            <Badge badgeContent={4} color="primary">
              <IconButton onClick={handleOpenUserMenu} sx={{ p: 0 }}>
                <Avatar alt="Remy Sharp" src="/images/account.svg" />
              </IconButton>
            </Badge>
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
            <MenuItem>
              <Typography textAlign="center">
                <Badge badgeContent={4} variant="dot" color="primary">
                  알림
                </Badge>
                <IconButton
                  aria-label="more"
                  id="long-button"
                  aria-controls={open ? "long-menu" : undefined}
                  aria-expanded={open ? "true" : undefined}
                  aria-haspopup="true"
                  onClick={handleClick}
                >
                  <MoreVertIcon />
                </IconButton>
                <Menu
                  id="long-menu"
                  MenuListProps={{
                    "aria-labelledby": "long-button",
                  }}
                  anchorEl={anchorEl}
                  open={open}
                  onClose={handleClose}
                  PaperProps={{
                    style: {
                      maxHeight: ITEM_HEIGHT * 4.5,
                      width: "20ch",
                    },
                  }}
                >
                  {options.map((option) => (
                    <MenuItem key={option} selected={option === "Pyxis"} onClick={handleClose}>
                      {option}
                    </MenuItem>
                  ))}
                </Menu>
              </Typography>
            </MenuItem>
          </Menu>
        </List>
      </Box>
    </>
  );
}
