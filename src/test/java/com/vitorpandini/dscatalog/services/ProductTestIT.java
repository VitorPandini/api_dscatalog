package com.vitorpandini.dscatalog.services;

import com.vitorpandini.dscatalog.repositories.ProductRepository;
import com.vitorpandini.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class ProductTestIT {
    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository repository;

    private long existingId;
    private Long nonExistingId;

    private Long countTotalProducts;

    @BeforeEach
    void setUp(){
        existingId=1L;
        nonExistingId=100L;

        countTotalProducts=25L;
    }

    @Test
    public void deleteShouldDeleteResourceWhenIdExists(){

        service.delete(existingId);

        assertEquals(countTotalProducts -1,repository.count());

    }
    @Test
    public void deleteShouldDeleteReturnThrow(){

        assertThrows(ResourceNotFoundException.class,() ->
                service.delete(nonExistingId)    );

    }

    @Test
    public void findAllPagedShouldReturnPageWhenPageAndSize(){
        PageRequest page = PageRequest.of(0,10);
        var result= service.findAllPaged(page);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(0,result.getNumber());
        Assertions.assertEquals(10,result.getSize());
        Assertions.assertEquals(countTotalProducts,result.getTotalElements());

    }
}
