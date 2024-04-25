import Navbar from "./navbar";
import styled from "@/styles/engineer.module.scss";

export default function Engineer() {
  return (
    <>
      <Navbar />
      <div className={styled.container}>
        <div className={styled.jigrelease}>지그 불출내역 컴포넌트</div>
        <div className={styled.jigrequest}>불출요청</div>
      </div>
    </>
  );
}
