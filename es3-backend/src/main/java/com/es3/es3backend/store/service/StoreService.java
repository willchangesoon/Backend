package com.es3.es3backend.store.service;

import com.es3.es3backend.config.exception.AuthException;
import com.es3.es3backend.config.exception.ErrorCode;
import com.es3.es3backend.config.exception.StoreException;
import com.es3.es3backend.constants.StoreStatus;
import com.es3.es3backend.seller.domain.Seller;
import com.es3.es3backend.seller.domain.SellerRepository;
import com.es3.es3backend.store.domain.Store;
import com.es3.es3backend.store.domain.StoreRepository;
import com.es3.es3backend.store.dto.StoreDto;
import com.es3.es3backend.store.dto.request.StoreCreateForm;
import com.es3.es3backend.store.dto.request.StoreUpdateRequest;
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
public class StoreService {
    private final StoreRepository storeRepository;
    private final SellerRepository sellerRepository;

    public void createStore(StoreCreateForm form, Seller seller) {
        Seller sellerIp = sellerRepository.getReferenceById(seller.getId());
        if (storeRepository.existsByName(form.name())) {
            throw new StoreException(ErrorCode.REGISTERED_NAME);
        }
        Store store = storeRepository.save(Store.builder()
                .name(form.name())
                .description(form.description())
                .logoImg(form.logoImg())
                .address(form.address())
                .contactNumber(form.contactNumber())
                .build());
        sellerIp.connectStore(store);
    }

    public StoreDto getStoreById(Seller seller, Long id) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
        if (seller != null && seller.getStore().equals(store)) {
            return StoreDto.fromEntity(store);
        }

        if (store.getStatus() != StoreStatus.ACTIVATE) {
            throw new StoreException(ErrorCode.NOT_AUTHORIZED_READ);
        }

        return StoreDto.fromEntity(store);
    }

    public List<StoreDto> getAvailableStores(Long cursor, int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by("id").descending());
        List<Store> stores = storeRepository.findAllByStatusAndByCursor(cursor, StoreStatus.ACTIVATE, pageable);
        return stores.stream().map(StoreDto::fromEntity).collect(Collectors.toList());
    }

    public void updateContactNumber(Seller seller, long id, StoreUpdateRequest.ContactNumber contactNumber) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
        seller = this.getSeller(seller);
        if (!seller.getStore().equals(store)){
            throw new StoreException(ErrorCode.NOT_AUTHORIZED);
        }

        store.updateContactNumber(contactNumber.getContactNumber());
    }

    public void updateAddress(Seller seller, long id, StoreUpdateRequest.Address address) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
        seller = this.getSeller(seller);
        if (!seller.getStore().equals(store)){
            throw new StoreException(ErrorCode.NOT_AUTHORIZED);
        }
        store.updateAddress(address.getAddress());
    }

    public void updateDescription(Seller seller, long id, StoreUpdateRequest.Description description) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
        seller = this.getSeller(seller);
        if (!seller.getStore().equals(store)){
            throw new StoreException(ErrorCode.NOT_AUTHORIZED);
        }
        store.updateDescription(description.getDescription());
    }

    public void updateLogoImg(Seller seller, long id, StoreUpdateRequest.LogoImg logoImg) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new StoreException(ErrorCode.STORE_NOT_FOUND));
        seller = this.getSeller(seller);
        if (!seller.getStore().equals(store)){
            throw new StoreException(ErrorCode.NOT_AUTHORIZED);
        }
        store.updateLogoImg(logoImg.getLogoImg());
    }

    private Seller getSeller(Seller seller) {
        if(seller == null) {
            throw new AuthException(ErrorCode.NOT_AUTHORIZED);
        }
        return sellerRepository.findById(seller.getId())
                .orElseThrow(() -> new AuthException(ErrorCode.USER_NOT_FOUND));
    }
}
