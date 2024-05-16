import React, {useEffect, useState} from "react";

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
import {useDashboardstore, useManagerGraphStore} from "@/store/dashboardstore";
import {set} from "immutable";



export default function App() {
    const {infos, fetchManagerGraph} = useManagerGraphStore()
    useEffect(() => {
        fetchManagerGraph()
            .then((res) => {
                console.log(res)
            })
            .catch((error) => {
                console.log(error.message)
            })
    }, []);
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
            data={infos}
            margin={{
                top: 20,
                right: 20,
                bottom: 20,
                left: 20
            }}
        >
            <defs>
                <linearGradient id="colorUvAmt" x1="0" y1="0" x2="0" y2="1">
                    <stop offset="5%" stopColor="#B0C4B1" stopOpacity={0.8}/>
                    <stop offset="95%" stopColor="#B0C4B1" stopOpacity={0.8}/>
                </linearGradient>
            </defs>
            <CartesianGrid stroke="#EBEAEA"/>
            <XAxis dataKey="day"/>
            <YAxis/>
            <Tooltip/>
            <Legend/>
            <Area
                type="monotone"
                dataKey="output"
                fill="#F9E8C9"
                stroke="none"
                opacity={0.15}
            />
            <Line type="monotone" dataKey="output" stroke="#3185FC" strokeWidth="3"/>
            <Line type="monotone" dataKey="cost" stroke="#5BBCFF" strokeWidth="3"/>
            <Bar dataKey="yield" barSize={15} fill="#201658"/>
            <Scatter dataKey="countMissingJig" fill="#FFFFFF"/>
        </ComposedChart>
    );
}