import Head from "next/head";
import Image from "next/image";
import { Inter } from "next/font/google";
import Stocklist from "@/components/release/StockList"
import styled from "@/styles/stocklist.module.css"
import Repairlist from "@/components/repair/List"
const inter = Inter({ subsets: ["latin"] });

export default function Home() {

  return (
      <>
          <div>
              <Repairlist/>
          </div>
      </>
  );
}
