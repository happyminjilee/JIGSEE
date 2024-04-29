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

export default function ManagerNavbar() {
  const [anchorElNav, setAnchorElNav] = React.useState<null | HTMLElement>(null);
  const [anchorElUser, setAnchorElUser] = React.useState<null | HTMLElement>(null);
  const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorElUser(event.currentTarget);
  };
  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };
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
                marginRight: "520px",
                cursor: "pointer",
              }}
            />
          </ListItem>
          <ListItem sx={{ paddingTop: "10px", paddingBottom: "0px", width: "95%" }}>
            <Button
              sx={{
                fontSize: "20px",
                color: "#40404A",
                fontWeight: "bold",
                width: "100%",
              }}
              href="/manager"
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
              <Typography textAlign="center">Logout</Typography>
            </MenuItem>
          </Menu>
        </List>
      </Box>
    </>
  );
}
