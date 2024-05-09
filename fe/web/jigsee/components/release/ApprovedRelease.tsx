import styled from "@/styles/modal/approvedrelease.module.css"
import {Button} from "@nextui-org/react"
import {useReleaseDetailStore, useReleaseModalStore} from "@/store/releasestore"

interface lst {
    model: string;
    model_name: string;
    count: number;
}


export default function ApprovedRelease() {
  const lst = [
      {model: 'A100512', model_name: 'razer', count: 5},
      {model: 'A100513', model_name: 'hammer', count: 5},
      {model: 'A100514', model_name: 'gas', count: 5},
      {model: 'A100515', model_name: 'nozzle', count: 5},
      {model: 'A100516', model_name: 'pad', count: 5},
      {model: 'A100517', model_name: 'bolts', count: 5},
      {model: 'A100518', model_name: 'nuts', count: 5},
      {model: 'A100519', model_name: 'mulnir', count: 5},
      {model: 'A100520', model_name: 'repulse', count: 5},
      {model: 'A100521', model_name: 'captain', count: 5},
  ]
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
                  { status === "PUBLISH" ? `수량 : ${lst.length}` :
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
