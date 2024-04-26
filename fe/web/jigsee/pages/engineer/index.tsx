import Navbar from "./navbar";
import styled from "@/styles/engineer.module.scss";
import Request from "@/components/release/Request";

export default function Engineer() {
  return (
    <>
      <Navbar />
      <div className={styled.container}>
        <div className={styled.jigrelease}>지그 불출내역 컴포넌트</div>
        <div className={styled.jigrequest}>
          <Request />
        </div>
      </div>
    </>
  );
}
