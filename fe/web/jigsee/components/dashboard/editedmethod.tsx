import React, { useEffect } from "react";
import { useDashboardstore } from "@/store/dashboardstore";
import { Dropdown, DropdownTrigger, DropdownMenu, DropdownItem, Button } from "@nextui-org/react";
import styled from "@/styles/dashboard/editmethod.module.scss";

export default function Editmethod() {
  const { getJigupdated, updatedList } = useDashboardstore();

  useEffect(() => {
    getJigupdated();
  }, []);

  return (
    <div className={styled.card}>
      <div className={styled.title}>점검 항목이 수정 된 Jig Model</div>
      <div className={styled.cardbody}>
        {updatedList.length > 0 ? (
          updatedList.map((item, index) => (
            <Dropdown key={index} className={styled.dropdown}>
              <DropdownTrigger>
                <Button variant="bordered">{item.model}</Button>
              </DropdownTrigger>
              <DropdownMenu aria-label="Static Actions">
                {item.checkItems.length > 0 ? (
                  item.checkItems.map((checkItem, idx) => (
                    <DropdownItem key={idx}>
                      <div>{checkItem.content}</div>
                      <div>{checkItem.standard}</div>
                    </DropdownItem>
                  ))
                ) : (
                  <DropdownItem>
                    <div>No check items available.</div>
                  </DropdownItem>
                )}
              </DropdownMenu>
            </Dropdown>
          ))
        ) : (
          <div>No updated items available.</div>
        )}
      </div>
    </div>
  );
}
