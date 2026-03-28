package com.fitbuddy.user_service.repositories;

import com.fitbuddy.user_service.dtos.UserDto;
import com.fitbuddy.user_service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsByEmail(String email);
}
