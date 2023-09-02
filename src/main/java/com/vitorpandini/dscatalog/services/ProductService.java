package com.vitorpandini.dscatalog.services;

import com.vitorpandini.dscatalog.dto.CategoryDTO;
import com.vitorpandini.dscatalog.dto.ProductDTO;
import com.vitorpandini.dscatalog.entities.Category;
import com.vitorpandini.dscatalog.entities.Product;
import com.vitorpandini.dscatalog.repositories.CategoryRepository;
import com.vitorpandini.dscatalog.repositories.ProductRepository;
import com.vitorpandini.dscatalog.services.exceptions.DatabaseException;
import com.vitorpandini.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll(){
        return repository.findAll().stream().map(ProductDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        var entity = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Nao encontrado o recurso!"));
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto){
        Product entity = new Product();
        copyDtoToEntity(dto,entity);

        entity =repository.save(entity);

        return new ProductDTO(entity);
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setDate(dto.getDate());
        entity.setImgUrl(dto.getImgUrl());

        entity.getCategories().clear();
        for (CategoryDTO dtoCat:dto.getCategories()) {
            Category category = categoryRepository.getOne(dtoCat.getId());
            entity.getCategories().add(category);

        }
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto){

        try{
            var entitySearched= repository.getOne(id);
            entitySearched.setName(dto.getName());
            copyDtoToEntity(dto,entitySearched);
            return new ProductDTO(entitySearched);

        }catch (EntityNotFoundException ex){
           throw  new ResourceNotFoundException("Id not found" + id);
        }


    }


    public void delete(Long id){

        try{
            repository.deleteById(id);
        }catch(EmptyResultDataAccessException ex){
            throw new ResourceNotFoundException("Id not found "+ id);
        }catch (DataIntegrityViolationException ex){
            throw new DatabaseException("Integrity Violation");
        }

    }


    public Page<ProductDTO> findAllPaged(Pageable pageRequest) {
        var listCat =repository.findAll(pageRequest);
        return listCat.map(ProductDTO::new);
    }
}
