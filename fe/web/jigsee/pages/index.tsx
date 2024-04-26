import Head from "next/head";
import Image from "next/image";
import { Inter } from "next/font/google";
import Stocklist from "@/components/release/StockList"
import styled from "@/styles/stocklist.module.css"
import StockList from "@/components/release/StockList";

const inter = Inter({ subsets: ["latin"] });
interface lst {
  model: string;
  count: number;
}

export default function Home() {

  return (
      <>
          <StockList/>
      </>
  );
}
