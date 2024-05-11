import styled from "@/styles/modal/approvedrelease.module.css"
import {Button} from "@nextui-org/react"
import {useReleaseDetailStore, useReleaseModalStore} from "@/store/releasestore"

interface lst {
    model: string;
    model_name: string;
    count: number;
}


export default function ApprovedRelease() {

  const {serialNos, status, updatedAt} = useReleaseDetailStore()
  const {isClose, setClose} = useReleaseModalStore()
  return (
      <>
          <div
              className={styled.container}
          >
              <div
                  className={styled.title}
              >
                  { status === "FINISH" ? "불출 승인": status === "PUBLISH" ? "승인 대기" :""}
              </div>
              <div
                  style={{margin: "0px auto 20px auto",
                      fontWeight: "bold",
                      fontSize: "medium"}}
              >
                  { status === "PUBLISH" ? `수량 : ${serialNos.length}` :
                      status ==="FINISH" ? `결재일 : ${updatedAt[0]}. ${updatedAt[1]}. ${updatedAt[2]}`:
                          <div></div>
                }
              </div>
              <div
                  className={styled.content}
              >
                  {serialNos.map((info) => (
                      <div
                          key={info}
                          style= {{
                              fontWeight: "bold",
                              fontSize: "large"
                          }}
                      >
                          {info}
                      </div>
                  ))}
              </div>

               <Button style={{
                   width: "300px",
                   height: "60px",
                   margin: "10px auto"
               }}
                       color="primary"
                       onPress={() => {setClose(false)}}
               >
                   확인
               </Button>

          </div>
      </>
  );
}
