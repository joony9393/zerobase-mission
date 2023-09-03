package com.zerobase.mission2.repository;

import com.zerobase.mission2.domain.Review;
import com.zerobase.mission2.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findByName(String name);
    List<Store> findByPartnerId(String partnerId);
}
