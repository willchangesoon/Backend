package com.es3.es3backend.store.domain;

import com.es3.es3backend.constants.StoreStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    boolean existsByName(String name);

    @Query("SELECT i FROM Store i WHERE (:cursor IS NULL OR i.id < :cursor) AND i.status = :status ORDER BY i.id DESC")
    List<Store> findAllByStatusAndByCursor(@Param("cursor") Long cursor, @Param("status") StoreStatus status, Pageable pageable);

}
