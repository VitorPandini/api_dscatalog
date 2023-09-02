package com.vitorpandini.dscatalog.services;

import com.vitorpandini.dscatalog.dto.CategoryDTO;
import com.vitorpandini.dscatalog.entities.Category;
import com.vitorpandini.dscatalog.repositories.CategoryRepository;
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
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        return repository.findAll().stream().map(CategoryDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id){
        var entity = repository.findById(id).orElseThrow(()->new ResourceNotFoundException("Nao encontrado o recurso!"));
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto){
        Category entity = new Category();
        entity.setName(dto.getName());
        entity =repository.save(entity);

        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto){

        try{
            var entitySearched= repository.getOne(id);
            entitySearched.setName(dto.getName());
            repository.save(entitySearched);
            return new CategoryDTO(entitySearched);

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


    public Page<CategoryDTO> findAllPaged(Pageable pageRequest) {
        var listCat =repository.findAll(pageRequest);
        return listCat.map(CategoryDTO::new);
    }
}
