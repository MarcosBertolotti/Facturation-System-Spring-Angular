package com.springboot.apirest.repository;

import com.springboot.apirest.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository<Client, Integer> {

    Boolean existsByEmail(String email);

}
