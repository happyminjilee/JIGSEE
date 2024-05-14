import React from "react";

import {
    ComposedChart,
    Line,
    Area,
    Bar,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
    Legend,
    Scatter
} from "recharts";



export default function App() {
    const data = [
        {
            name: "Page A",
            uv: 590,
            pv: 800,
            amt: 1400,
            cnt: 490
        },
        {
            name: "Page B",
            uv: 868,
            pv: 967,
            amt: 1506,
            cnt: 590
        },
        {
            name: "Page C",
            uv: 1397,
            pv: 1098,
            amt: 989,
            cnt: 350
        },
        {
            name: "Page D",
            uv: 1480,
            pv: 1200,
            amt: 1228,
            cnt: 480
        },
        {
            name: "Page E",
            uv: 1520,
            pv: 1108,
            amt: 1100,
            cnt: 460
        },
        {
            name: "Page F",
            uv: 1400,
            pv: 680,
            amt: 1700,
            cnt: 380
        },
        {
            name: "Page G",
            uv: 1400,
            pv: 680,
            amt: 1700,
            cnt: 380
        },
        {
            name: "Page H",
            uv: 1400,
            pv: 680,
            amt: 1700,
            cnt: 380
        },
        {
            name: "Page I",
            uv: 1400,
            pv: 680,
            amt: 1700,
            cnt: 380
        },
        {
            name: "Page J",
            uv: 1400,
            pv: 680,
            amt: 1700,
            cnt: 380
        },
        {
            name: "Page K",
            uv: 1400,
            pv: 680,
            amt: 1700,
            cnt: 380
        }
    ];
    return (
        <ComposedChart
            width={1500}
            height={340}
            data={data}
            margin={{
                top: 20,
                right: 20,
                bottom: 20,
                left: 20
            }}
        >
            <CartesianGrid stroke="#f5f5f5" />
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Legend />
            <Area
                type="monotone"
                dataKey="amt"
                fill="rgba(255, 115, 0, 0.5)"
                stroke="none"
            />
            <Area
                type="monotone"
                dataKey="uv"
                fill="rgba(255, 255, 255, 1)"
                stroke="none"
            />
            <Line type="monotone" dataKey="uv" stroke="#ff7300" />
            <Line type="monotone" dataKey="amt" stroke="#2F76FF" />

            <Bar dataKey="pv" barSize={20} fill="#413ea0" />
            <Scatter dataKey="cnt" fill="red" />
        </ComposedChart>
    );
}