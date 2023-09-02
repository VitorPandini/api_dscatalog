package com.vitorpandini.dscatalog.resources.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class ValidationError extends StandardError{


    private List<FieldMessage> errors= new ArrayList<>();


    public void addErrors(String FieldName,String message){
        this.errors.add(new FieldMessage(FieldName,message));
    }
}
