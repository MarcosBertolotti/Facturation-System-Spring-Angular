package com.springboot.apirest.repository;

import com.springboot.apirest.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends CrudRepository<Product, Integer> {

    //@Query("select p from Product p where p.name like %?1%")    // si ponemos % en ambos lados, busca la letras en cualquier parte de la cadena
    //List<Product> findByName(String term);

    List<Product> findByNameContainingIgnoreCase(String term);  // busca la palabra en cualquier parte de la cadena ignorado si es mayuscula o minuscula
}
