import Navbar from "./navbar";
import styled from "@/styles/engineer.module.scss";
import Request from "@/components/release/Request";
import ReleaseStatusList from "@/components/release/ReleaseStatusList";
export default function Engineer() {
  return (
    <>
      <Navbar />
      <div className={styled.container}>
        <div className={styled.jigrelease}>
          <ReleaseStatusList />
        </div>
        <div className={styled.jigrequest}>
          <Request />
        </div>
      </div>
    </>
  );
}
