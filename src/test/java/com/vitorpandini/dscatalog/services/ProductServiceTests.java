package com.vitorpandini.dscatalog.services;

import com.vitorpandini.dscatalog.dto.ProductDTO;
import com.vitorpandini.dscatalog.entities.Product;
import com.vitorpandini.dscatalog.repositories.ProductRepository;
import com.vitorpandini.dscatalog.services.exceptions.DatabaseException;
import com.vitorpandini.dscatalog.services.exceptions.ResourceNotFoundException;
import com.vitorpandini.dscatalog.tests.Factory;
import org.h2.engine.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private long existingId;

    private long nonExistingId;

    private long dependentId;

    private PageImpl<Product>page ;

    private Product product;

    @BeforeEach
    void setUp()throws Exception{
        existingId=1L;
        dependentId=4L;
        nonExistingId=100L;
        product = Factory.createProduct();
        page = new PageImpl<>(List.of(product));


        Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);

        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
    }

    @Test
    public void findAllPagedShouldReturnPage(){
        var pageable = PageRequest.of(0,10);

        Page<ProductDTO> result = service.findAllPaged(pageable);

        Assertions.assertNotNull(result);

        Mockito.verify(repository,Mockito.times(1)).findAll(pageable);
    }

    @Test
    public void deleteShouldDatabaseExceptionWhenDependentId(){


        Assertions.assertThrows(DatabaseException.class,() ->{
            service.delete(dependentId);
        });

        Mockito.verify(repository,Mockito.times(1)).deleteById(dependentId);

    }

    @Test
    public void deleteShouldResourceNotFoundExceptionWhenIdDoesNotExists(){


        Assertions.assertThrows(ResourceNotFoundException.class,() ->{
            service.delete(nonExistingId);
        });

        Mockito.verify(repository,Mockito.times(1)).deleteById(nonExistingId);

    }
    @Test
    public void deleteShouldDoNothingWhenIdExists(){

        Assertions.assertDoesNotThrow(() ->{
            service.delete(existingId);
        });

        Mockito.verify(repository,Mockito.times(1)).deleteById(existingId);
    }

}
