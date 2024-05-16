import React, { useEffect, useState } from "react";
import ReactApexChart from "react-apexcharts";
import { ApexOptions } from "apexcharts";
import { useDashboardstore } from "@/store/dashboardstore";
interface state {
  series: number[];
  options: ApexOptions;
}
const ApexChart = () => {
  const { getJignumbers, deleted, request, finish } = useDashboardstore();
  const [repairing, setRepairing] = useState(0);
  const [newfinish, setNewfinish] = useState(0);
  const [newdelete, setNewdelete] = useState(0);
  useEffect(() => {
    getJignumbers()
      .then((res) => {
        const newRepairing = request - deleted - finish;
        setRepairing(newRepairing);
        setNewdelete(deleted);
        setNewfinish(finish);
      })
      .catch((error) => {
        console.log("failed to get Jig numbers", error);
      });
  }, [request, deleted, finish]); // Include dependencies to avoid stale closures

  const state: state = {
    series: [newfinish, repairing, newdelete],
    options: {
      chart: {
        type: "donut",
        width: "100%",
        toolbar: {
          show: false,
        },
      },
      title: {
        text: "이번달 수리 현황",
        align: "center",
        margin: 20,
      },
      labels: ["수리완료", "수리중", "폐기"],
      colors: ["#15b79f", "#635bff", "#fb9c0c"],
      legend: {
        position: "bottom",
        horizontalAlign: "center",
        offsetY: 10,
        markers: {
          width: 12,
          height: 12,
          radius: 5,
        },
        itemMargin: {
          vertical: 5,
        },
      },
      responsive: [
        {
          breakpoint: 480,
          options: {
            chart: {
              width: "100%",
            },
            legend: {
              fontSize: "10px",
              offsetY: 0,
            },
          },
        },
      ],
    },
  };

  return (
    <ReactApexChart width="380px" options={state.options} series={state.series} type="donut" />
  );
};

export default ApexChart;
