package com.delremi.repository;

import com.delremi.model.Client;
import com.delremi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    List<Client> findAllByCreatedBy(User createdBy);

    Client findByIdAndCreatedBy(int id, User createdBy);
}
