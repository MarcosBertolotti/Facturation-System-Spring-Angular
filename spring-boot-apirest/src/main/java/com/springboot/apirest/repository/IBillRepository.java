package com.springboot.apirest.repository;

import com.springboot.apirest.entity.Bill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBillRepository extends CrudRepository<Bill, Integer> {

}
