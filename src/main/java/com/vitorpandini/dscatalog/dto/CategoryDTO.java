package com.vitorpandini.dscatalog.dto;

import com.vitorpandini.dscatalog.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class CategoryDTO implements Serializable {
    private static final long serialVersionUID=1L;

    private Long id;

    private String name;

    public CategoryDTO(Category entity){
        this.id = entity.getId();
        this.name= entity.getName();
    }



}
