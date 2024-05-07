import {useDrop, useDrag} from "react-dnd";
import React, {useRef} from "react";
import styled from "@/styles/repairrequest.module.css";
import {useCompoStore, useWoDetailStore} from "@/store/workorderstore";
import {useCartStore, useGroupFilter, useMartStore} from "@/store/repairrequeststore";
import {set} from "immutable";

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
    items: ItemProps[],
    boxType: string,
}

interface Item {
    item: ItemProps,
    originBox: string,
}

export const DropBox = ({items, boxType}: Items ) => {
    const {martList ,addToMart, removeFromMart} = useMartStore()
    const {cartList, addToCart, removeFromCart, clearCartList} = useCartStore()
    const {addForFilter, addForMart, removeForMart} = useGroupFilter()
    const ref = useRef(null);
    const [{isOver, canDrop}, drop] = useDrop({
        accept: "cargo",    //수용가능한 아이템 타입
        drop: (item, monitor) => {
            const droppedItem = monitor.getItem() as Item
            if (droppedItem && droppedItem.originBox !== boxType) {
                if (droppedItem.item.status !== "PUBLISH") {
                    window.alert("이미 요청된 Jig 입니다.")
                } else {
                    console.log("Item dropped:", item);
                    if (boxType === "Cart") {
                        addToCart(droppedItem.item)
                        if (martList) {
                            removeForMart(droppedItem.item)
                        }
                    } else if (boxType === "Mart") {
                        addForMart(droppedItem.item)
                        if (cartList) {
                            removeFromCart(droppedItem.item)
                        }
                    }
                }

            }
            return {boxType}
        }, // 아이템이 드롭될때 반환할 객체
        hover: () => {},
        collect: (monitor) => ({
            isOver: monitor.isOver(),
            canDrop: monitor.canDrop()
        }),


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
    console.log(cartList)

    return (
        <div
            ref={ref}
            style={{backgroundColor: getBackgroundColor()}}
            className={styled.contents}
        >
            {items.map((item) => (
                <DragItem item={item} key={item.id} originBox={boxType}/>
            ))}

        </div>
    )
}



const DragItem = ({item, originBox} : Item) => {
    const {setWoId, rightCompo, setRightCompo} = useCompoStore()
    const {fetchWoDetail, id} = useWoDetailStore()
    const cardClick = (Id: number, state: string) => {
        // 클릭한 S/N로 아이디로 바꾸기 , 추후 수정 예정
        console.log("cardclick", state)
        console.log("cardclick", rightCompo)
        fetchWoDetail(Id)
            .then((res) => {
                console.log('woDetail', res)
            })
            .catch((error) => {
                console.log(error.message)
            })
            .finally(() => {
                setWoId(Id);
                setRightCompo(state);
                console.log(id)
            })
    };
    ////////////////////////////////////////////////////////////////////////////

    const ref = useRef(null);
    const [{isDragging}, drag] = useDrag({
        type: "cargo",
        item: {item, originBox},
        collect: (monitor) => ({
            isDragging: monitor.isDragging(),
        }),
        end: (draggedItem, monitor) => {
            const dropResult = monitor.getDropResult();
            if (dropResult) {
                console.log('dropped', dropResult)
            }
        }
    })
    const cardStyle = isDragging ? {opacity:0.7, cursor: 'grab'} : {};
    drag(ref)

    return (
        <div
            ref={ref}
            key={item.id}
            className={styled.card}
            style={{...cardStyle}}
            onClick={() => {
                cardClick(item.id, item.status)
                console.log(item.id, item.status)
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