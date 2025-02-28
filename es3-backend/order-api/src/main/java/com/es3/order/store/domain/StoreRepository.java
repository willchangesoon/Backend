package com.es3.order.store.domain;

import com.es3.order.store.StoreStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    boolean existsByName(String name);

    @Query("SELECT i FROM Store i WHERE (:cursor IS NULL OR i.id < :cursor) AND i.status = :status ORDER BY i.id DESC")
    List<Store> findAllByStatusAndByCursor(@Param("cursor") Long cursor, @Param("status") StoreStatus status, Pageable pageable);

    Optional<Store> findBySellerId(Long sellerId);

    Optional<Store> findByIdAndSellerId(Long storeId, Long sellerId);
}
