package com.springboot.apirest.services;

import com.springboot.apirest.entity.Client;
import com.springboot.apirest.entity.ErrorResponse;
import com.springboot.apirest.entity.Region;
import com.springboot.apirest.exceptions.ClientNotFoundException;
import com.springboot.apirest.exceptions.ObjectAlreadyExistsException;
import com.springboot.apirest.repository.IClientRepository;
import com.springboot.apirest.repository.IRegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.util.Optional.ofNullable;

@Service
public class ClientServiceImpl implements IClientService {

    private static final String CLIENT_NOT_FOUND = "Client with id '%s' doesn't exists";

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IRegionRepository regionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {

        return clientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable page) {

        return clientRepository.findAll(page);
    }

    @Override
    @Transactional(readOnly = true)
    public Client findById(final Integer id) throws ClientNotFoundException{

        return clientRepository.findById(id)
                .orElseThrow(ClientNotFoundException::new);
    }

    @Override
    public Client save(final Client client) {

        ofNullable(clientRepository.existsByEmail(client.getEmail()))
                .filter(FALSE::equals)
                .orElseThrow(() -> new ObjectAlreadyExistsException(Client.class.getSimpleName(), "email", client.getEmail()));

        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public Client update(final Client client, final Integer id) throws ClientNotFoundException {

        Client c = this.findById(id);

        c.setFirstName(client.getFirstName());
        c.setLastName(client.getLastName());
        c.setEmail(client.getEmail());
        c.setRegion(client.getRegion());

        return clientRepository.save(c);
    }

    @Override
    @Transactional
    public Client deleteById(Integer id) throws ClientNotFoundException {

        Client client = this.findById(id);
        clientRepository.deleteById(id);

        return client;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Region> findAllRegions() {

        return regionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsByEmail(String email){

        return ofNullable(email)
                .map(clientRepository::existsByEmail)
                .orElse(false);
    }
}
