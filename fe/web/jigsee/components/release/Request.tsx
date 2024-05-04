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
  // dummy jig serial 가져오기
  const serialNo = [
    "5254-245",
    "0942-6487",
    "52125-206",
    "14783-304",
    "67692-332",
    "76237-247",
    "35356-807",
    "13668-030",
    "68306-101",
    "64141-111",
    "49647-0001",
    "42254-178",
    "36987-1806",
    "65321-030",
    "54868-6185",
    "51531-8365",
    "13537-108",
    "55154-3030",
    "59762-3328",
    "55154-4798",
    "54973-1129",
    "50419-701",
    "0472-0163",
    "30142-686",
    "55292-122",
    "67253-388",
    "41250-029",
    "52544-950",
    "54738-905",
    "51079-075",
  ];

  // 체크된 아이템을 관리하는 상태
  const [checked, setChecked] = React.useState<readonly string[]>([]);
  // 왼쪽 리스트를 관리하는 상태
  const [left, setLeft] = React.useState<readonly string[]>(serialNo);
  // 입력 필드에서의 사용자 입력 상태
  const [filter, setFilter] = useState("");
  // 오른쪽 리스트를 관리하는 상태
  const [right, setRight] = React.useState<readonly string[]>([]);
  // 입력 필드 값이 변경될 때마다 필터링 로직 실행
  useEffect(() => {
    if (filter === "") {
      setLeft(serialNo); // 필터가 비어있다면 전체 리스트를 보여줌
    } else {
      const filteredSerials = serialNo.filter((s) => s.startsWith(filter));
      setLeft(filteredSerials); // 필터에 맞는 결과로 상태 업데이트
    }
  }, [filter]);

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
            <ListItemButton key={value} role="listitem" onClick={handleToggle(value)}>
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
              <ListItemText id={labelId} primary={`${value + 1}`} />
            </ListItemButton>
          );
        })}
      </List>
    </Paper>
  );

  const [selectedFacility, setSelectedFacility] = useState("");
  const [selectedModel, setSelectedModel] = useState("");
  // 설비명 리스트 더미
  const facilities = ["line cutter", "raser", "metalize"];
  // 모델명 리스트 더미
  const models = ["swf235430", "dfdg456872", "ddg24652"];

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
                <SelectItem key={facility} value={facility}>
                  {facility}
                </SelectItem>
              ))}
            </Select>
            <Select
              variant="bordered"
              label="Jig model"
              labelPlacement="outside-left"
              placeholder="모델을 선택 하세요"
              className={styled.select}
              onChange={(e) => setSelectedModel(e.target.value)}
            >
              {models.map((model) => (
                <SelectItem key={model} value={model}>
                  {model}
                </SelectItem>
              ))}
            </Select>
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
          <button className={styled.addbtn}>요청</button>
        </CardFooter>
      </Card>
    </>
  );
}
