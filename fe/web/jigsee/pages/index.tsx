import Head from "next/head";
import Image from "next/image";
import { Inter } from "next/font/google";

import Wo from "@/components/workorder/template"
const inter = Inter({ subsets: ["latin"] });

export default function Home() {

  return (
      <>
          <div>
              <Wo/>
          </div>
      </>
  );
}
