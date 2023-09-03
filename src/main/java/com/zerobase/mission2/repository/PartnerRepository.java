package com.zerobase.mission2.repository;

import com.zerobase.mission2.domain.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, String> {
    Optional<Partner> findByUsername(String name);
}
