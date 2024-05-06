import {useDrop, useDrag} from "react-dnd";
import React, {useRef} from "react";
import styled from "@/styles/repairrequest.module.css";
import {useCompoStore, useWoDetailStore} from "@/store/workorderstore";

interface ItemProps {
    id: number,
    model: string, // 지그 모델명
    serialNo: string, // 지그 일련번호
    creator: string, // 작성자
    terminator: string, // 작성 종료자
    status: string, // wo 상태
    createdAt: number[], // wo 생성시간
    updatedAt: string, // wo 수정시간
}

interface Items {
    items: ItemProps[]
}

export const DropBox = ({items}: Items,  ) => {
    const moveItemHandler = () => {

    }
    const ref = useRef(null);
    const [{isOver, canDrop}, drop] = useDrop({
        accept: "cargo",    //수용가능한 아이템 타입
        drop: (item, monitor) => {
            if (monitor.canDrop()) {
                console.log("Item dropped:", item);
            }
        }, // 아이템이 드롭될때 반환할 객체
        hover: () => {},
        collect: (monitor) => ({
            isOver: monitor.isOver(),
            canDrop: monitor.canDrop()
        }),
        // 아이템이 해당 칸에 들어갈 수 있는지 판단하는 로직
        canDrop: (item) => {
            return true
        },

    })
    const getBackgroundColor = () => {
        if (isOver) {
            if (canDrop) {
                return "lightgray";
            } else {
                return "white"
            }
        } else {
            return "white"
        }
    }
    drop(ref);
    return (
        <div
            ref={ref}
    style={{backgroundColor: getBackgroundColor()}}
    className={styled.contents}
        >
        {items.map((item) => (
                <DragItem item={item} key={item.id}/>
))}

    </div>
)
}



const DragItem = ({item} : {item:ItemProps}) => {
    const {setWoId, rightCompo , setRightCompo} = useCompoStore()
    const {fetchWoDetail} = useWoDetailStore()
    const cardClick = (Id: number, state: string) => {
        // 클릭한 S/N로 아이디로 바꾸기 , 추후 수정 예정
        setWoId("testModelId");
        setRightCompo(state);
        console.log("cardclick", state)
        console.log("cardclick", rightCompo)
        fetchWoDetail(Id)
            .then((res) => {
                console.log('woDetail', res)
            })
            .catch((error) => {
                console.log(error.message)
            })
    };
    const ref = useRef(null);
    const [{isDragging}, drag] = useDrag({
        type: "cargo",
        item: {id: item.id},
        collect: (monitor) => ({
            isDragging: monitor.isDragging(),
        }),
        end: (monitor) => {

        }
    })
    const cardStyle = isDragging ? {opacity:0.5} : {};
    drag(ref)
    return (
        <div
            ref={ref}
    key={item.id}
    className={styled.card}
    style={{...cardStyle}}
    onClick={() => {
        cardClick(item.id, item.status)
    }}>
    <div className={styled.division1}>
    <div className={styled.date}>
        {item.createdAt[0]}.{item.createdAt[1]}.{item.createdAt[2]}
    </div>
    <div className={styled.title}>
        {item.serialNo} | {item.model}
        </div>
        </div>

        <div className={styled.division2}>{item.status}</div>
        </div>
)
}