package com.springboot.apirest.services;

import com.springboot.apirest.entity.Client;
import com.springboot.apirest.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClientService {

    List<Client> findAll();

    Page<Client> findAll(Pageable page);

    Client findById(Integer id);

    Client save(Client client);

    Client update(Client client, Integer id);

    Client deleteById(Integer id);

    List<Region> findAllRegions();

    Boolean existsByEmail(String email);
}
