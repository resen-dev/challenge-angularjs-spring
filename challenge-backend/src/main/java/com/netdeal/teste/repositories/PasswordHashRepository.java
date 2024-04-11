package com.netdeal.teste.repositories;

import com.netdeal.teste.models.PasswordHashModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordHashRepository extends JpaRepository<PasswordHashModel, Long> {
}
