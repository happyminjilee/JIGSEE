import { useState, useEffect } from "react";
import Engineerdashboard from "./engineer";
import Managerdashboard from "./manager";

export default function DashBoard() {
  const [role, setRole] = useState<string>("");
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const storedRole = localStorage.getItem("role");
    if (storedRole !== null) {
      setRole(storedRole);
    }
    setIsLoading(false);
  }, []);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  return (
    <>{role === "MANAGER" ? <Managerdashboard /> : <Engineerdashboard />}</>
  );
}
