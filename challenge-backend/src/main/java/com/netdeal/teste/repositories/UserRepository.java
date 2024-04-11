package com.netdeal.teste.repositories;

import com.netdeal.teste.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    Set<UserModel> findByNameContaining(String name);

    @Query("SELECT u FROM UserModel u WHERE u.superior IS NULL")
    Set<UserModel> getSuperiors();

    @Query("SELECT u FROM UserModel u WHERE u.superior.id = :id")
    Set<UserModel> getSubordinates(Long id);
}
