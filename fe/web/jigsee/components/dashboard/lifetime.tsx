import React, { useState, useEffect } from "react";
import ReactApexChart from "react-apexcharts";
import { ApexOptions } from "apexcharts";
import { useDashboardstore } from "@/store/dashboardstore";
export default function Lifetime() {
  const [model, setModel] = useState("");
  const { jigmodel } = useDashboardstore();
  useEffect(() => {
    setModel(jigmodel);
  }, [jigmodel]);
  // 차트에 표시할 데이터
  const series = [
    {
      name: "Desktops",
      data: [3500, 3300, 3200, 3000, 2900, 2800, 2760, 2654, 2500],
    },
  ];

  // 차트 옵션 설정
  const options: ApexOptions = {
    chart: {
      height: 350,
      type: "line",
      zoom: {
        enabled: false,
      },
    },
    dataLabels: {
      enabled: false,
    },
    stroke: {
      curve: "straight",
    },
    title: {
      text: `수리 횟수에 따른 예상 생명주기 ${model}`,
      align: "left",
    },
    grid: {
      row: {
        colors: ["#f3f3f3", "transparent"], // takes an array which will be repeated on columns
        opacity: 0.5,
      },
    },
    xaxis: {
      categories: ["1회", "2회", "3회", "4회", "5회", "6회", "7회", "8회", "9회"],
    },
  };

  // ReactApexChart 컴포넌트를 사용하여 차트 렌더링
  return (
    <div>
      <ReactApexChart width="500px" options={options} series={series} type="line" height={270} />
    </div>
  );
}
