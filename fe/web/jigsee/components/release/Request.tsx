import React, { useState, useEffect } from "react";
import styled from "@/styles/jigrequest.module.scss";
import { Card, CardHeader, CardBody, CardFooter } from "@nextui-org/react";
import { Select, SelectItem, Divider, Input } from "@nextui-org/react";
import Grid from "@mui/material/Grid";
import List from "@mui/material/List";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import Checkbox from "@mui/material/Checkbox";
import Button from "@mui/material/Button";
import Paper from "@mui/material/Paper";
import { useFacilityStore } from "@/store/facilitystore";
import { releaseRequest } from "@/pages/api/releaseAxios";
import { useRouter } from "next/router";
//transfer list 함수
// 배열 a에서 배열 b에 없는 항목만 반환
function not(a: readonly string[], b: readonly string[]) {
  return a.filter((value) => b.indexOf(value) === -1);
}

// 두 배열 a와 b의 교집합을 반환
function intersection(a: readonly string[], b: readonly string[]) {
  return a.filter((value) => b.indexOf(value) !== -1);
}
export default function Request() {
  const router = useRouter();
  const [selectedFacility, setSelectedFacility] = useState("");
  const [selectedModel, setSelectedModel] = useState("");

  const { loadFacilities, facilities, getJigSN, jigmodels } =
    useFacilityStore();
  useEffect(() => {
    loadFacilities();
    if (selectedFacility) {
      getJigSN(selectedFacility);
    }
  }, [selectedFacility]);
  // 체크된 아이템을 관리하는 상태
  const [checked, setChecked] = React.useState<readonly string[]>([]);
  // 왼쪽 리스트를 관리하는 상태
  const [left, setLeft] = React.useState<readonly string[]>(jigmodels);
  // 입력 필드에서의 사용자 입력 상태
  const [filter, setFilter] = useState("");
  // 오른쪽 리스트를 관리하는 상태
  const [right, setRight] = React.useState<string[]>([]);
  // jigmodels가 변경될 때마다 left를 업데이트
  useEffect(() => {
    setLeft(jigmodels);
  }, [jigmodels]);
  // 필터에 따라 리스트 업데이트
  useEffect(() => {
    if (filter === "") {
      setLeft(jigmodels); // 필터가 비어있다면 전체 리스트를 보여줌
    } else {
      const filteredSerials = jigmodels.filter((s) => s.startsWith(filter));
      setLeft(filteredSerials); // 필터에 맞는 결과로 상태 업데이트
    }
  }, [filter, jigmodels]); // filter와 jigmodels 변화에 반응

  // 왼쪽 리스트에서 체크된 아이템
  const leftChecked = intersection(checked, left);
  // 오른쪽 리스트에서 체크된 아이템
  const rightChecked = intersection(checked, right);

  // 특정 아이템의 체크 상태를 토글하는 함수
  const handleToggle = (value: string) => () => {
    const currentIndex = checked.indexOf(value);
    const newChecked = [...checked];

    if (currentIndex === -1) {
      newChecked.push(value);
    } else {
      newChecked.splice(currentIndex, 1);
    }

    setChecked(newChecked);
  };

  // 모든 아이템을 오른쪽으로 이동
  // const handleAllRight = () => {
  //   setRight(right.concat(left));
  //   setLeft([]);
  // };

  // 체크된 아이템만 오른쪽으로 이동
  const handleCheckedRight = () => {
    setRight(right.concat(leftChecked));
    setLeft(not(left, leftChecked));
    setChecked(not(checked, leftChecked));
  };

  // 체크된 아이템만 왼쪽으로 이동
  const handleCheckedLeft = () => {
    setLeft(left.concat(rightChecked));
    setRight(not(right, rightChecked));
    setChecked(not(checked, rightChecked));
  };

  // 모든 아이템을 왼쪽으로 이동
  // const handleAllLeft = () => {
  //   setLeft(left.concat(right));
  //   setRight([]);
  // };

  // 리스트 아이템을 렌더링하는 함수
  const customList = (items: readonly string[]) => (
    <Paper sx={{ width: 200, height: 230, overflow: "auto" }}>
      <List dense component="div" role="list">
        {items.map((value: string) => {
          const labelId = `transfer-list-item-${value}-label`;

          return (
            <ListItemButton
              key={value}
              role="listitem"
              onClick={handleToggle(value)}
            >
              <ListItemIcon>
                <Checkbox
                  checked={checked.indexOf(value) !== -1}
                  tabIndex={-1}
                  disableRipple
                  inputProps={{
                    "aria-labelledby": labelId,
                  }}
                />
              </ListItemIcon>
              <ListItemText id={labelId} primary={`${value}`} />
            </ListItemButton>
          );
        })}
      </List>
    </Paper>
  );
  // 불출 요청
  const sendReleaseList = () => {
    releaseRequest(right)
      .then((res) => {
        if (res === true) {
          // releaseRequest 함수가 성공적으로 완료되면 새로고침
          alert("불출 요청이 완료 되었습니다.");
          window.location.reload();
        } else if (res === false) {
          // releaseRequest 함수가 성공적으로 완료되면 새로고침
          alert("요청 사항을 다시 검토 해주세요");
          window.location.reload();
        }
      })
      .catch((error) => {});
  };

  return (
    <>
      <Card className={styled.requestcontainer}>
        <CardHeader className={styled.cardheader}>불출 요청</CardHeader>
        <Divider className={styled.headerline} />
        <CardBody>
          <div className={styled.cardbody}>
            <Select
              variant="bordered"
              label="설비 이름"
              labelPlacement="outside-left"
              placeholder="설비를 선택 하세요"
              onChange={(e) => setSelectedFacility(e.target.value)}
              className={styled.select}
            >
              {facilities.map((facility) => (
                <SelectItem key={facility.model} value={facility.model}>
                  {facility.model}
                </SelectItem>
              ))}
            </Select>
            {/* {jigmodels.length > 0 && (
              <Select
                variant="bordered"
                label="Jig model"
                labelPlacement="outside-left"
                placeholder="모델을 선택하세요"
                className={styled.select}
                onChange={(e) => setSelectedModel(e.target.value)}
              >
                {jigmodels.map((jig) => (
                  <SelectItem key={jig} value={jig}>
                    {jig}
                  </SelectItem>
                ))}
              </Select>
            )} */}
            <Input
              type="string"
              variant="bordered"
              labelPlacement="outside-left"
              label="serial number"
              className={styled.amountinput}
              // 입력값에 따라 필터
              onChange={(e) => setFilter(e.target.value)}
            />
            <div className={styled.labelcontainer}>
              <label htmlFor="불출 가능">불출 가능</label>
              <label htmlFor="불출 요청">불출 요청</label>
            </div>
          </div>

          <div className={styled.btncontainer}>
            <Grid
              sx={{ mt: "3px" }}
              container
              spacing={2}
              justifyContent="center"
              alignItems="center"
            >
              <Grid item>{customList(left)}</Grid>
              <Grid item>
                <Grid container direction="column" alignItems="center">
                  {/* <Button
                    sx={{ my: 0.5 }}
                    variant="outlined"
                    size="small"
                    onClick={handleAllRight}
                    disabled={left.length === 0}
                    aria-label="move all right"
                  >
                    ≫
                  </Button> */}
                  <Button
                    sx={{ my: 0.5 }}
                    variant="outlined"
                    size="small"
                    onClick={handleCheckedRight}
                    disabled={leftChecked.length === 0}
                    aria-label="move selected right"
                  >
                    &gt;
                  </Button>
                  <Button
                    sx={{ my: 0.5 }}
                    variant="outlined"
                    size="small"
                    onClick={handleCheckedLeft}
                    disabled={rightChecked.length === 0}
                    aria-label="move selected left"
                  >
                    &lt;
                  </Button>
                  {/* <Button
                    sx={{ my: 0.5 }}
                    variant="outlined"
                    size="small"
                    onClick={handleAllLeft}
                    disabled={right.length === 0}
                    aria-label="move all left"
                  >
                    ≪
                  </Button> */}
                </Grid>
              </Grid>
              <Grid item>{customList(right)}</Grid>
            </Grid>
          </div>
        </CardBody>
        <Divider />
        <CardFooter className={styled.cardfooter}>
          <button onClick={sendReleaseList} className={styled.addbtn}>
            요청
          </button>
        </CardFooter>
      </Card>
    </>
  );
}
