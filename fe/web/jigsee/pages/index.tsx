import { useEffect } from "react";
import { useRouter } from "next/router";
import Loading from "./loading";
export default function Home() {
  const router = useRouter();

  useEffect(() => {
    const timer = setTimeout(() => {
      router.push("/login");
    }, 3000); // 3초 후 리디렉션

    return () => clearTimeout(timer); // 컴포넌트 언마운트 시 타이머 정리
  }, [router]);

  return <Loading />;
}
