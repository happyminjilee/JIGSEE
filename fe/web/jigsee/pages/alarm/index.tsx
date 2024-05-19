import { Pagination } from "@nextui-org/react";
import { useState, useEffect } from "react";
import Switch from "@mui/material/Switch";
import alarmStyle from "@/styles/alarm.module.scss";
import { useAlarmStore } from "@/store/ssestore";
import LocalPostOfficeIcon from "@mui/icons-material/LocalPostOffice";
import DraftsIcon from "@mui/icons-material/Drafts";
import EngineerNav from "@/pages/engineer/navbar";
import ManagerNav from "@/pages/manager/navbar";

export default function Alarm() {
  // switch checked 확인 변수
  const [checked, setChecked] = useState(true);
  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setChecked(event.target.checked);
  };
  // 알람 스토어 변수 들
  const {
    alarmId,
    uncheckednumber,
    uncheckedList,
    setUnchecked,
    setAlarmCheck,
  } = useAlarmStore();
  const { setAllalarm, alarmList, setPage, page } = useAlarmStore();
  const checkClick = (ID: number) => {
    setAlarmCheck(ID);
  };

  // 알림 get test
  useEffect(() => {
    setUnchecked();
  }, [alarmId]);
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
            {checked && <label>미확인 알림 {uncheckednumber}</label>}
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
            <div className={alarmStyle.scrollcontainer}>
              {uncheckedList.map((item, index) => (
                <div
                  key={index}
                  className={alarmStyle.fullWidth}
                  onClick={() => checkClick(item.id)}
                >
                  {item.notificationStatus}{" "}
                  <div>
                    요청자{item.sender}
                    {item.checkStatus === false && (
                      <LocalPostOfficeIcon color="primary" />
                    )}
                  </div>
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
                onClick={() => checkClick(item.id)}
              >
                {item.notificationStatus} | 요청자{item.sender}
                {item.checkStatus === false && (
                  <LocalPostOfficeIcon color="primary" />
                )}
                {item.checkStatus === true && <DraftsIcon />}
              </div>
            ))}
            <div className={alarmStyle.center}>
              <Pagination onChange={(e) => setPage(e)} total={5} />
            </div>
          </div>
        )}
      </div>
    </>
  );
}
