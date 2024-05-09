import { Pagination, button, divider } from "@nextui-org/react";
import { useState, useEffect } from "react";
import Switch from "@mui/material/Switch";
import alarmStyle from "@/styles/alarm.module.scss";
import { useAlarmStore } from "@/store/ssestore";
import LocalPostOfficeIcon from "@mui/icons-material/LocalPostOffice";
import DraftsIcon from "@mui/icons-material/Drafts";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import { Typography } from "@mui/material";
import EngineerNav from "@/pages/engineer/navbar";
import ManagerNav from "@/pages/manager/navbar";
interface InfoAlarm {
  checkStatus: boolean;
  contentId: string;
  id: number;
  notificationStatus: string;
  receiver: string;
  sender: string;
}
export default function Alarm() {
  const [openModal, setOpenModal] = useState(false);

  const defaultInfoAlarm: InfoAlarm = {
    checkStatus: false, // Default boolean value
    contentId: "", // Default string value
    id: 0, // Default number value
    notificationStatus: "", // Default string value
    receiver: "", // Default string value
    sender: "", // Default string value
  };

  // alarm detail
  const [cardItem, setCarditem] = useState<InfoAlarm>(defaultInfoAlarm);

  // switch checked 확인 변수
  const [checked, setChecked] = useState(true);
  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setChecked(event.target.checked);
  };
  // 알람 스토어 변수 들
  const { alarmId, uncheckednumber, uncheckedList, setUnchecked, setAlarmCheck } = useAlarmStore();
  const { setAllalarm, all_alarams, alarmList, setPage, page } = useAlarmStore();
  const checkClick = (ID: number, item: InfoAlarm) => {
    console.log(ID);
    setAlarmCheck(ID);
    setOpenModal(true);
    setCarditem(item);
  };
  // 모달 닫기 버튼
  const closeModal = () => {
    setOpenModal(false);
  };
  // 알림 get test
  useEffect(() => {
    setUnchecked();
  }, []);
  // 알림 get test
  useEffect(() => {
    setAllalarm();
  }, [page, checked]);

  // navbar 렌더링 로직
  const [role, setRole] = useState<string>(""); // 초기 상태를 명시적으로 string 타입으로 설정

  useEffect(() => {
    // 컴포넌트가 클라이언트 사이드에서 마운트되었을 때 로컬 스토리지에서 role 읽기
    const storedRole = localStorage.getItem("role");
    if (storedRole !== null) {
      setRole(storedRole); // 로컬 스토리지의 값이 null이 아닌 경우에만 상태 업데이트
    }
  }, []);
  let Navbar;
  if (role === "MANAGER") {
    Navbar = <ManagerNav />;
  } else if (role === "ENGINEER") {
    Navbar = <EngineerNav />;
  } else {
    Navbar = <ManagerNav />; // 기본값으로 ManagerNav 설정
  }
  return (
    <>
      {Navbar}
      <div className={alarmStyle.bigcontainer}>
        <div className={alarmStyle.switch}>
          <div className={alarmStyle.switchlabel}>
            {checked && <label>미확인 알림</label>}
            {checked === false && <label>전체 알림</label>}
            <Switch
              checked={checked}
              onChange={handleChange}
              inputProps={{ "aria-label": "controlled" }}
            />
          </div>
        </div>

        {checked ? (
          <div className={alarmStyle.container}>
            <div className={alarmStyle.numberalarm}>미확인 알람 {uncheckednumber}</div>
            <div className={alarmStyle.scrollcontainer}>
              {uncheckedList.map((item, index) => (
                <div
                  key={index}
                  className={alarmStyle.fullWidth}
                  onClick={() => checkClick(item.id, item)}
                >
                  {item.notificationStatus} | 요청자{item.sender}
                  {item.checkStatus === false && <LocalPostOfficeIcon color="primary" />}
                </div>
              ))}
            </div>
          </div>
        ) : (
          <div className={alarmStyle.container}>
            {alarmList.map((item, index) => (
              <div
                key={index}
                className={alarmStyle.fullWidth}
                onClick={() => checkClick(item.id, item)}
              >
                {item.notificationStatus} | 요청자{item.sender}
                {item.checkStatus === false && <LocalPostOfficeIcon color="primary" />}
                {item.checkStatus === true && <DraftsIcon />}
              </div>
            ))}
            <div className={alarmStyle.center}>
              <Pagination onChange={(e) => setPage(e)} total={5} />
            </div>
          </div>
        )}
      </div>

      {/* 디테일 보기 */}
      <Modal
        open={openModal} // Corrected from 'open'
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
        sx={{
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        <Box
          sx={{
            width: "100%",
            height: "80%",
            display: "flex",
            alignItems: "start",
            justifyContent: "center",
          }}
        >
          {/* 닫기 아이콘 */}

          <Box
            sx={{
              width: "500px",
              height: "300px",
              display: "flex",
              flexDirection: "column",
              alignItems: "start",
              justifyContent: "center",
              backgroundColor: "var(--realwhite)",
              border: "1px solid var(--samsungblue)",
              borderRadius: "5px",
            }}
          >
            id : {cardItem.id}
            <Typography variant="h6" sx={{ marginTop: "5px" }}>
              {" "}
              요청 번호 : {cardItem.contentId}
            </Typography>
            <Typography sx={{ width: "100%", marginTop: "50px" }} align="right">
              {" "}
              요청자 : {cardItem.sender}
            </Typography>
            <Typography sx={{ width: "100%" }} align="right">
              {" "}
              수신인 : {cardItem.receiver}
            </Typography>
            <Typography color="primary" sx={{ width: "100%", marginTop: "30px" }} align="center">
              {" "}
              요청 사항{" "}
            </Typography>
            <Typography color="primary" sx={{ width: "100%" }} align="center" variant="h4">
              {" "}
              {cardItem.notificationStatus}
            </Typography>
          </Box>
          <img
            src="/images/delete_gray.svg"
            alt="delete"
            onClick={closeModal}
            style={{ width: "50px", height: "50px" }}
          ></img>
        </Box>
      </Modal>
    </>
  );
}
