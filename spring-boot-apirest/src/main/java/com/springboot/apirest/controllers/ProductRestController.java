package com.springboot.apirest.controllers;

import com.springboot.apirest.entity.Product;
import com.springboot.apirest.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private IProductService productService;

    @Secured("ROLE_ADMIN")
    @GetMapping("/filter/{term}")
    public ResponseEntity<List<Product>> filterProducts(@PathVariable final String term) {

        return ok(productService.findByNameContainingIgnoreCase(term));
    }
}
