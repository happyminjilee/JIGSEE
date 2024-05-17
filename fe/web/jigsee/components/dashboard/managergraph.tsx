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
    const [list, setList] = useState([])
    useEffect(() => {
        fetchManagerGraph()
            .then((res) => {
                console.log(res)
            })
            .catch((error) => {
                console.log(error.message)
            })
    }, []);

    return (
        <ComposedChart
            width={1500}
            height={340}
            data={infos.slice(10, 30)}
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
            {/*<Area*/}
            {/*    type="monotone"*/}
            {/*    dataKey="output"*/}
            {/*    fill="#F9E8C9"*/}
            {/*    stroke="none"*/}
            {/*    opacity={0.15}*/}
            {/*/>*/}
            <Line type="monotone" dataKey="output" stroke="#3185FC" strokeWidth="3"/>
            <Line type="monotone" dataKey="cost" stroke="#5BBCFF" strokeWidth="3"/>
            <Bar dataKey="yield" barSize={15} fill="#201658"/>
            <Line dataKey="countMissingJig" fill="#F9E8C9" strokeWidth="3"/>
        </ComposedChart>
    );
}