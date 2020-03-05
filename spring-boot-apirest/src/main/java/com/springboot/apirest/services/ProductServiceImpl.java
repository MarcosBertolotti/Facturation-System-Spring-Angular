package com.springboot.apirest.services;

import com.springboot.apirest.entity.Product;
import com.springboot.apirest.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findByNameContainingIgnoreCase(String term) {

        return productRepository.findByNameContainingIgnoreCase(term);
    }
}
