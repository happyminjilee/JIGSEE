import React, { Component } from "react";
import ReactApexChart from "react-apexcharts";
import { ApexOptions } from "apexcharts";

interface State {
  series: number[];
  options: ApexOptions;
}

class ApexChart extends Component<{}, State> {
  constructor(props: {}) {
    super(props);

    this.state = {
      series: [45, 30, 5],
      options: {
        chart: {
          type: "donut",
          width: "100%",
          toolbar: {
            show: false, // Optionally disable toolbar for cleaner look
          },
        },
        title: {
          text: "이번달 수리 현황",
          align: "center",
          margin: 20, // Adjust title margin to give space for the legend below
        },
        labels: ["수리완료", "수리중", "폐기"],
        colors: ["#15b79f", "#635bff", "#fb9c0c"],
        legend: {
          position: "bottom",
          horizontalAlign: "center", // Centers the legend items horizontally
          offsetY: 10, // Adds vertical offset for the legend, adjusts spacing below chart
          markers: {
            width: 12,
            height: 12,
            radius: 5, // Adjust marker shape and size
          },
          itemMargin: {
            vertical: 5, // Adds spacing between legend items
          },
        },
        responsive: [
          {
            breakpoint: 480,
            options: {
              chart: {
                width: "100%", // Change width from pixels to percentage for responsiveness
              },
              legend: {
                fontSize: "10px", // Smaller font size for smaller screens
                offsetY: 0, // Reduce or remove offset at smaller screen sizes
              },
            },
          },
        ],
      },
    };
  }

  render() {
    return (
      <ReactApexChart
        width="380px"
        options={this.state.options}
        series={this.state.series}
        type="donut"
      />
    );
  }
}

export default ApexChart;
