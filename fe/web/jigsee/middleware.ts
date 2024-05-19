import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";
import { getCookie } from "./utils/cookie";

export function middleware(request: NextRequest) {
  // 리다이렉트 조건
  if (!request.cookies.get("refresh_token")) {
    return NextResponse.redirect(new URL("/login", request.url));
  }
}

export const config = {
  // 이 Middleware가 동작할 경로들을 추가해주면된다.
  matcher: [
    "/common/:path*",
    "/dashboard/:path*",
    "/alarm/:path*",
    "/manager/:path*",
  ],
};
