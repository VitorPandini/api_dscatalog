package com.vitorpandini.dscatalog.tests;

import com.vitorpandini.dscatalog.dto.ProductDTO;
import com.vitorpandini.dscatalog.entities.Category;
import com.vitorpandini.dscatalog.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct(){
        Product product = new Product(1L,"Phone","Good Phone",800.0,"https://img.com/img.png", Instant.parse("2020-10-20T03:00:002"));
        product.getCategories().add(new Category(2L,"Eletronics"));
        return product;
    }

    public static ProductDTO createProductDTO(){
        return new ProductDTO(createProduct(),createProduct().getCategories());
    }
}
