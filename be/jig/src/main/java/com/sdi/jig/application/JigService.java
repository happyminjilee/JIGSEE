package com.sdi.jig.application;

import com.sdi.jig.dto.response.JigResponseDto;
import com.sdi.jig.entity.JigNosqlEntity;
import com.sdi.jig.entity.JigRDBEntity;
import com.sdi.jig.repository.JigNosqlRepository;
import com.sdi.jig.repository.JigRDBRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JigService {

    private final JigRDBRepository jigRDBRepository;
    private final JigNosqlRepository jigNosqlRepository;

    public JigResponseDto findByModel(String model) {
        JigRDBEntity rdb = jigRDBRepository.findByModel(model)
                .orElseThrow(() -> new IllegalArgumentException("모델을 찾을 수 없습니다."));
        JigNosqlEntity nosql = jigNosqlRepository.findById(model)
                .orElseThrow(() -> new IllegalArgumentException("모델을 찾을 수 없습니다."));

        return JigResponseDto.from(rdb, nosql);
    }
}
