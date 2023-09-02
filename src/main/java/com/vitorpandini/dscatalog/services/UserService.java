package com.vitorpandini.dscatalog.services;

import com.vitorpandini.dscatalog.dto.*;
import com.vitorpandini.dscatalog.entities.Role;
import com.vitorpandini.dscatalog.entities.User;
import com.vitorpandini.dscatalog.repositories.RoleRepository;
import com.vitorpandini.dscatalog.repositories.UserRepository;
import com.vitorpandini.dscatalog.services.exceptions.DatabaseException;
import com.vitorpandini.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    public List<UserDTO> findAll(){
        return repository.findAll().stream().map(UserDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id){
        var entity = repository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Nao encontrado o recurso!"));
        return new UserDTO(entity);
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto){
        User entity = new User();
        copyDtoToEntity(dto,entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity =repository.save(entity);

        return new UserDTO(entity);
    }

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        ;


        entity.getRoles().clear();
        for (RoleDTO roleDTO:dto.getRoles()) {
            Role role = roleRepository.getOne(roleDTO.getId());
            entity.getRoles().add(role);

        }
    }

    @Transactional
    public UserDTO update(Long id, UserUpdateDTO dto){

        try{
            var entitySearched= repository.getOne(id);
            entitySearched.setFirstName(dto.getFirstName());
            copyDtoToEntity(dto,entitySearched);
            return new UserDTO(entitySearched);

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


    public Page<UserDTO> findAllPaged(Pageable pageRequest) {
        var listCat =repository.findAll(pageRequest);
        return listCat.map(UserDTO::new);
    }
}
