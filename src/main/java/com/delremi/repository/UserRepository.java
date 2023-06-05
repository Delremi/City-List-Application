package com.delremi.repository;

import com.delremi.model.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Integer> {
    CustomUser findByUsername(String username);

    CustomUser findById(int id);
}
