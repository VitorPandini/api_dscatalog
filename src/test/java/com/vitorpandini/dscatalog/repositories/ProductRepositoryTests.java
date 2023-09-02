package com.vitorpandini.dscatalog.repositories;

import com.vitorpandini.dscatalog.entities.Product;
import com.vitorpandini.dscatalog.tests.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private Long nonExistsId;

    private Long existsId;

    private Long countTotalProducts;


    @BeforeEach
    public void  setUp()throws Exception{
        nonExistsId =100L;
        existsId=1L;
        countTotalProducts=25L;


    }
    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull(){
        Product product = Factory.createProduct();
        product.setId(null);

        product=repository.save(product);

        Assertions.assertNotNull(product.getId());
        Assertions.assertSame(product.getId(), countTotalProducts+1);
        Assertions.assertEquals(countTotalProducts+1,product.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){


        repository.deleteById(existsId);
        var result =repository.findById(existsId);

        Assertions.assertFalse(result.isPresent());

    }
    @Test
    public void deleteShouldDeleteObjectWhenIdNotExists(){

        Assertions.assertThrows(EmptyResultDataAccessException.class, () ->{

            repository.deleteById(nonExistsId);
        });

    }
}
