package com.springboot.apirest.repository;

import com.springboot.apirest.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegionRepository extends JpaRepository<Region,Integer> {

}
