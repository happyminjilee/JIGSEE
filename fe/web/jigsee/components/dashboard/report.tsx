import styled from "@/styles/dashboard/report.module.scss";

export default function Report() {
  return (
    <>
      <div className={styled.report}>
        <div className={styled.card}>
          <div className={styled.title}>이번달 폐기된 Jig</div>
          <div className={styled.views}>
            4<span className={styled.percentage}>↑ 5.3%</span>
          </div>
        </div>
        <div className={styled.card}>
          <div className={styled.title}>이번달 교체된 Jig</div>
          <div className={styled.views}>
            80<span className={styled.percentage}>↑ 9.4%</span>
          </div>
        </div>
        <div className={styled.card}>
          <div className={styled.title}>수리 요청 Jig</div>
          <div className={styled.views}>
            94<span className={styled.percentage}>↑ 60.3%</span>
          </div>
        </div>
        <div className={styled.card}>
          <div className={styled.title}>수리 완료 Jig</div>
          <div className={styled.views}>
            74<span className={styled.percentage}>↑ 7.3%</span>
          </div>
        </div>
      </div>
    </>
  );
}
