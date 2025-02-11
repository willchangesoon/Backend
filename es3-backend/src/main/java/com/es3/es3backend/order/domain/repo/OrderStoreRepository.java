package com.es3.es3backend.order.domain.repo;

import com.es3.es3backend.order.domain.OrderStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderStoreRepository extends JpaRepository<OrderStore, Long> {
    List<OrderStore> findByStoreId(Long id);

    Optional<OrderStore> findByStoreIdAndId(Long storeId, Long orderStoreId);
}
