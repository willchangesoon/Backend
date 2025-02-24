package com.es3.order.store.service;

import com.es3.order.config.exception.ErrorCode;
import com.es3.order.config.exception.StoreException;
import com.es3.order.store.domain.Store;
import com.es3.order.store.domain.StoreRepository;
import com.es3.order.store.dto.StoreDto;
import com.es3.order.store.dto.request.StoreCreateForm;
import com.es3.order.store.dto.request.StoreUpdateRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StoreService {
    private final StoreRepository storeRepository;

    public void createStore(StoreCreateForm form, String sellerId) {
        if (storeRepository.existsByName(form.name())) {
            throw new StoreException(ErrorCode.REGISTERED_NAME);
        }
        storeRepository.save(Store.builder()
                .name(form.name())
                .description(form.description())
                .logoImg(form.logoImg())
                .address(form.address())
                .contactNumber(form.contactNumber())
                .build());
    }

    public void updateContactNumber(String sellerId, long id, StoreUpdateRequest.ContactNumber contactNumber) {
        Store store = validate(sellerId, id);

        store.updateContactNumber(contactNumber.getContactNumber());
    }

    public void updateAddress(String sellerId, long id, StoreUpdateRequest.Address address) {
        Store store = validate(sellerId, id);
        store.updateAddress(address.getAddress());
    }

    public void updateDescription(String sellerId, long id, StoreUpdateRequest.Description description) {
        Store store = validate(sellerId, id);
        store.updateDescription(description.getDescription());
    }

    public void updateLogoImg(String sellerId, long id, StoreUpdateRequest.LogoImg logoImg) {
        Store store = validate(sellerId, id);
        store.updateLogoImg(logoImg.getLogoImg());
    }

    private Store validate(String sellerId, long id) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
        Store sellerStore =  storeRepository.findBySellerId(Long.valueOf(sellerId))
                .orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
        if (!sellerStore.equals(store)){
            throw new StoreException(ErrorCode.NOT_AUTHORIZED);
        }
        return store;
    }

    public StoreDto getSellerStore(String sellerId) {
        return StoreDto.fromEntity(storeRepository.findBySellerId(Long.valueOf(sellerId))
                .orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND)));
    }

}
