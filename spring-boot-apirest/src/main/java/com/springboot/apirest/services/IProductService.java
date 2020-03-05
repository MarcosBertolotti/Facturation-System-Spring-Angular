package com.springboot.apirest.services;

import com.springboot.apirest.entity.Product;

import java.util.List;

public interface IProductService {

    List<Product> findByNameContainingIgnoreCase(String term);
}
