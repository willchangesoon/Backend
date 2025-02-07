package com.es3.es3backend.store.service;

import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.config.exception.StoreException;
import com.es3.es3backend.constants.StoreStatus;
import com.es3.es3backend.store.domain.Store;
import com.es3.es3backend.store.domain.StoreRepository;
import com.es3.es3backend.store.dto.StoreDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StoreCommonService {

    private final StoreRepository storeRepository;

    public StoreDto getStoreById(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
        if (store.getStatus() != StoreStatus.ACTIVATE) {
            throw new StoreException(ErrorCode.NOT_AUTHORIZED_READ);
        }
        return StoreDto.fromEntityFilterBanner(store);
    }

    public List<StoreDto> getAvailableStores(Long cursor, int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by("id").descending());
        List<Store> stores = storeRepository.findAllByStatusAndByCursor(cursor, StoreStatus.ACTIVATE, pageable);
        return stores.stream().map(StoreDto::fromEntityFilterBanner).collect(Collectors.toList());
    }

}
