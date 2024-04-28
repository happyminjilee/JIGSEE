package com.sdi.common.service;

import com.sdi.common.dto.JigItemStatusUpdateDto;
import com.sdi.common.dto.MessageDto;
import com.sdi.common.dto.response.RepairJigDetailResponseDto;
import com.sdi.common.dto.response.RepairJigListResponseDto;
import com.sdi.common.dto.response.RequestJigDetailResponseDto;
import com.sdi.common.dto.request.RepairJigRequestDto;
import com.sdi.common.dto.request.RequestJigRequestDto;
import com.sdi.common.dto.request.ResponseJigRequestDto;
import com.sdi.common.dto.response.RequestJigListResponseDto;
import com.sdi.common.entity.RepairRequestEntity;
import com.sdi.common.entity.WantRequestEntity;
import com.sdi.common.entity.WantResponseEntity;
import com.sdi.common.repository.RepairRequestsRepository;
import com.sdi.common.repository.WantRequestsRepository;
import com.sdi.common.repository.WantResponsesRepository;
import com.sdi.common.util.JigItemClient;
import com.sdi.common.util.MessageClient;
import com.sdi.common.util.SseStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestService {
    private static final int PAGE_SIZE = 5;
    /*
     * 현재 API 게이트웨이에 알림서버의 api를 호출하는 메소드가 없으므로 연결 불가능
     *
     *
     */
    private final WantRequestsRepository wantRequestsRepository;
    private final WantResponsesRepository wantResponsesRepository;
    private final RepairRequestsRepository repairRequestsRepository;
    private final MessageClient messageClient;
    private final JigItemClient jigItemClient;


    // Transactional 왜 적용이 안될까?
    // 알림 전송 실패하면 mongo에도 저장되면 안되는데..
    @Transactional
    public void createWantRequest(RequestJigRequestDto requestJigRequestDto) {
        /*
         * 1. 요청 내용 DB에 저장
         * 2. 알림 서버로 알림 전송 요청 보냄
         */
        WantRequestEntity content = wantRequestsRepository.save(WantRequestEntity.from(requestJigRequestDto));
        messageClient.sendMessage(MessageDto.of(SseStatus.REQUEST_JIG, requestJigRequestDto.sender(), content.getId()));
    }

    @Transactional
    public void createWantResponse(ResponseJigRequestDto responseJigRequestDto) {
        /*
         * 1. 승인인지 거부인지 판별
         * 2. 승인이라면 불출된 지그 전부 상태 변경(투입 대기)
         * 3. 원본 요청의 상태를 FINISH로 변경
         * 4. 응답 내용 DB에 저장
         * 5. 알림 서버로 알림 전송 요청 보냄
         */
        if (responseJigRequestDto.isAccept()) {
            for (String serialNo : responseJigRequestDto.serialNos()) {
                jigItemClient.changeJigStatusToReady(JigItemStatusUpdateDto.ready(serialNo));
            }
        }
        WantRequestEntity originalRequest = wantRequestsRepository.findById(responseJigRequestDto.requestId())
                .orElseThrow(() -> new IllegalArgumentException("원본 요청을 찾을 수 없습니다."));
        originalRequest.processRequest(responseJigRequestDto.sender(), responseJigRequestDto.isAccept());
        wantRequestsRepository.save(originalRequest);
        WantResponseEntity content = wantResponsesRepository.save(WantResponseEntity.from(responseJigRequestDto));
        messageClient.sendMessage(MessageDto.of(SseStatus.RESPONSE_JIG, responseJigRequestDto.sender(), content.getId()));
    }

    @Transactional
    public void createRepairRequest(RepairJigRequestDto repairJigRequestDto) {
        /*
         * 1. 요청 내용 DB에 저장
         * 상태 변경은 다른 곳에서 하는거겠지?
         * 2. 알림 서버로 알림 전송 요청 보냄
         */
        RepairRequestEntity content = repairRequestsRepository.save(RepairRequestEntity.from(repairJigRequestDto));
        messageClient.sendMessage(MessageDto.of(SseStatus.REQUEST_REPAIR, repairJigRequestDto.sender(), content.getId()));
    }


    public RequestJigListResponseDto findAllWantJigRequests(String option, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.by("time").descending());
        Page page = switch (option) {
            case "PUBLISH", "FINISH" -> wantRequestsRepository.findAllByStatus(option, pageable);
            case "REJECT" -> wantRequestsRepository.findAllByIsAcceptAndStatus(false, "FINISH", pageable);
            case "", "ALL" -> wantRequestsRepository.findAll(pageable);
            default -> throw new IllegalStateException("Unexpected value: " + option);
        };

        List<RequestJigDetailResponseDto> dtoList = ((List<WantRequestEntity>) page.getContent()).stream()
                .map(entity -> RequestJigDetailResponseDto.from(entity))
                .collect(Collectors.toList());

        return RequestJigListResponseDto.of(page.getNumber() + 1, page.getTotalPages(), dtoList);
    }

    public RequestJigDetailResponseDto findOneJigRequest(String requestId) {
        return RequestJigDetailResponseDto.from(
                wantRequestsRepository.findById(requestId)
                        .orElseThrow(() -> new IllegalArgumentException("원본 요청을 찾을 수 없습니다.")));
    }

    public RepairJigListResponseDto findAllRepairRequests(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.by("time").descending());
        Page page = repairRequestsRepository.findAll(pageable);

        List<RepairJigDetailResponseDto> list = ((List<RepairRequestEntity>) page.getContent()).stream()
                .map(entity -> RepairJigDetailResponseDto.from(entity))
                .collect(Collectors.toList());

        return RepairJigListResponseDto.of(page.getNumber() + 1, page.getTotalPages(), list);
    }

    public RepairJigDetailResponseDto findOneRepairRequest(String requestId) {
        return RepairJigDetailResponseDto.from(
                repairRequestsRepository.findById(requestId)
                        .orElseThrow(() -> new IllegalArgumentException("원본 요청을 찾을 수 없습니다."))
        );
    }
}
