package com.zerobase.mission2.repository;

import com.zerobase.mission2.domain.Uesr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Uesr, String> {
    Optional<Uesr> findByUsername(String name);
}
