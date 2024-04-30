import Head from "next/head";
import Image from "next/image";
import { Inter } from "next/font/google";
import { getjigMethod } from "@/pages/api/jigAxios";

import Wo from "@/components/workorder/template";
const inter = Inter({ subsets: ["latin"] });

export default function Home() {
  const getApitest = () => {
    const result = getjigMethod("testModelID");
    console.log(result);
  };
  return (
    <>
      <div>
        <button onClick={getApitest}>jigmodel 검색 </button>
      </div>
    </>
  );
}
